package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.auth.JwtFilter;
import daangnmarket.daangn.project.auth.TokenProvider;
import daangnmarket.daangn.project.domain.member.Authority;
import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.dto.MemberDTO;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import java.util.Collections;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final CustomUserDetailsService customUserDetailsService;

    public Long signUp(MemberDTO.SignUpDto signUpDto){
        Boolean isDuplicatedUsername = memberRepository.existsByUsername(signUpDto.getUsername());
        // TODO: 회원이 이미 존재할 경우 Exception 처리해야 함
        if(isDuplicatedUsername){
            return null;
        }else {
            Authority authority = Authority.builder().authorityName("ROLE_ADMIN").build();
            Member member = Member.builder()
                    .email(signUpDto.getEmail())
                    .password(passwordEncoder.encode(signUpDto.getPassword()))
                    .username(signUpDto.getUsername())
                    .nickname(signUpDto.getNickname())
                    .activated(true)
                    .authorities(Collections.singleton(authority))
                    .build();
            memberRepository.save(member);
            return member.getId();
        }
    }

    public ResponseEntity<MemberDTO.TokenDTO> login(@Valid @RequestBody MemberDTO.LoginDTO memberLoginDto) {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) customUserDetailsService.loadUserByUsername(memberLoginDto.getUsername());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, memberLoginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        String username = userDetails.getUsername();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(username, authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new MemberDTO.TokenDTO(jwt), httpHeaders, HttpStatus.OK);
    }


    // username을 통해 해당 유저의 정보 및 권한 정보를 리턴
    @Transactional(readOnly = true)
    public MemberDTO.infoDTO getUserWithAuthorities(String username) {
        return MemberDTO.infoDTO.from(memberRepository.findOneWithAuthoritiesByUsername(username));
    }

    // 위에서 작성한 SecurityUtil의 getCurrentUsername() 메서드를 통해 username의 유저 및 권한 정보를 리턴
    @Transactional(readOnly = true)
    public MemberDTO.infoDTO getMyUserWithAuthorities() {
        return MemberDTO.infoDTO.from(memberRepository.findOneWithAuthoritiesByUsername(SecurityUtil.getCurrentUsername()
                .orElseThrow(() -> new IllegalArgumentException("로그인 유저 정보를 가져오는 데 실패했습니다."))));
    }
}
