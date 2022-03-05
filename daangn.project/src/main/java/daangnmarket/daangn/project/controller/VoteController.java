package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.vote.Vote;
import daangnmarket.daangn.project.dto.vote.*;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.service.MemberService;
import daangnmarket.daangn.project.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/votes")
public class VoteController {
    private final VoteService voteService;
    @PostMapping
    public ResponseEntity<VoteSaveDto> voteCreate(@RequestBody VoteSaveDto voteSaveDto) throws IOException {
        voteService.save(voteSaveDto);
        return ResponseEntity.ok(voteSaveDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteResponseDto> voteDetails(@PathVariable Long id) {
        VoteResponseDto voteResponseDto = new VoteResponseDto(voteService.findById(id));
        voteService.getResultOfVote(voteResponseDto);
        return ResponseEntity.ok(voteResponseDto);
    }

    @PutMapping
    public ResponseEntity<Long> voteModify(@RequestBody VoteUpdateDto voteUpdateDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> voteRemove(@PathVariable String id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> voteParticipate(@PathVariable Long id, @RequestBody VoteParticipateDto voteParticipateDto) {
        if(voteService.participate(id, voteParticipateDto))
            return ResponseEntity.ok(voteParticipateDto);
        return new ResponseEntity<>(Message.builder().status(StatusEnum.BAD_REQUEST).message("이미 투표하셨습니다.").build(), HttpStatus.BAD_REQUEST);

    }
}
