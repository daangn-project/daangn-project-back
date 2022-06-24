package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.member.Authority;
import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.dto.MemberDTO;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.util.SecurityUtil;
import daangnmarket.daangn.project.vo.SignVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:sss");
    Date time = new Date();

    public Long signUp(SignVo signVo){
        Boolean isDuplicatedUsername = memberRepository.existsByUsername(signVo.getUsername());
        // TODO: 회원이 이미 존재할 경우 Exception 처리해야 함
        if(isDuplicatedUsername){
            return null;
        }else {
            Authority authority = Authority.builder().authorityName("ROLE_ADMIN").build();
            Member member = Member.builder()
                    .email(signVo.getEmail())
                    .password(passwordEncoder.encode(signVo.getPassword()))
                    .username(signVo.getUsername())
                    .nickname(signVo.getNickname())
                    .activated(true)
                    .authorities(Collections.singleton(authority))
                    .build();
            memberRepository.save(member);
            return member.getId();
        }
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
