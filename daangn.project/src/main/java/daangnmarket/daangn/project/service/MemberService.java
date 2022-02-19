package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.ItemPost;
import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.dto.MemberSaveDto;
import daangnmarket.daangn.project.message.ApiResponse;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.vo.SignVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = format.format(time);

    public Long save(SignVo signVo){
        Optional<Member> alreadyMember = memberRepository.findByUsername(signVo.getUsername());
        if(alreadyMember.isPresent()){
            return alreadyMember.get().getId();
        }else {
            Member member = Member.builder()
                    .email(signVo.getEmail())
                    .password(signVo.getPassword())
                    .username(signVo.getUsername())
                    .nickname(signVo.getNickname())
                    .appendDate(localTime)
                    .updateDate(localTime)
                    .build();

            member.setPassword(new BCryptPasswordEncoder().encode(member.getPassword()));
            memberRepository.save(member);
            return member.getId();
        }
    }

    public Member findById(Long id){
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
    }

    public Member findByUsername(String username){
        return memberRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("유저가 없음"));
    }

}
