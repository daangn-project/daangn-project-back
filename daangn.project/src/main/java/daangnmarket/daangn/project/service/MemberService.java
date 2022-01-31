package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.dto.MemberSaveDto;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public Member findById(Long id){
        System.out.println("id = " + id);
        Member member = memberRepository.findAll().get(0);
        System.out.println("member = " + member);
        return memberRepository.findById(id).get();
    }

    public Optional<Member> findUserByEmail(String name){
        Optional<Member> member = Optional.ofNullable(memberRepository.findByNickname(name));
        return member;
    }
}
