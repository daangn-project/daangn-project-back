package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.Authority;
import daangnmarket.daangn.project.domain.Member;
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
    String localTime = format.format(time);

    public Long signup(SignVo signVo){
        Optional<Member> alreadyMember = memberRepository.findByUsername(signVo.getUsername());
        if(alreadyMember.isPresent()){
            return alreadyMember.get().getId();
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
        return MemberDTO.infoDTO.from(memberRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    // 위에서 작성한 SecurityUtil의 getCurrentUsername() 메서드를 통해 username의 유저 및 권한 정보를 리턴
    @Transactional(readOnly = true)
    public MemberDTO.infoDTO  getMyUserWithAuthorities() {
        return MemberDTO.infoDTO.from(SecurityUtil.getCurrentUsername().flatMap(memberRepository::findOneWithAuthoritiesByUsername).orElse(null));
    }


    public Member findById(Long id){
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
    }

    public Member findByUsername(String username){
        return memberRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("유저가 없음"));
    }
    public Member findByNickname(String nickname){
        return memberRepository.findByUsername(nickname).orElseThrow(() -> new IllegalArgumentException("유저가 없음"));
    }
}
