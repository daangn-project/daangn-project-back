package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.vote.Vote;
import daangnmarket.daangn.project.dto.vote.*;
import daangnmarket.daangn.project.service.MemberService;
import daangnmarket.daangn.project.service.VoteService;
import lombok.RequiredArgsConstructor;
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
        voteService.getResultForVote(voteResponseDto);
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
    public ResponseEntity<VoteParticipateDto> voteParticipate(@PathVariable Long id, @RequestBody VoteParticipateDto voteParticipateDto) {
        // voteOptionId를 조회하여 해당 투표 결과에 회원의 이름 추가
        voteService.participate(voteParticipateDto);
        return ResponseEntity.ok(voteParticipateDto);
    }
}
