package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.ItemPost;
import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.dto.MemberSaveDto;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;


    public ResponseEntity<Message> join(MemberSaveDto memberSaveDto){
        Member newMember = memberSaveDto.toEntity();
        Member savedMember = memberRepository.save(newMember);
        return new ResponseEntity<>(Message.builder().status(StatusEnum.OK).message("회원가입이 완료되었습니다").build(), HttpStatus.OK);
    }

    public void save(Member member){
        Optional<Member> alreadyMember = memberRepository.findByEmail(member.getEmail());
        if(alreadyMember.isPresent()){
            //여기서 예외처리?
        }
        member.setPassword(new BCryptPasswordEncoder().encode(member.getPassword()));
        memberRepository.save(member);
    }
    public Member findById(Long id){
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
    }

    public Member findByUsername(String name){
        return memberRepository.findByUsername(name).orElseThrow(() -> new IllegalArgumentException("유저가 없음"));
    }

}
