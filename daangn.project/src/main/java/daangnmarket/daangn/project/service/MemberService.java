package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.ItemPost;
import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.dto.MemberSaveDto;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


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
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
    }
}
