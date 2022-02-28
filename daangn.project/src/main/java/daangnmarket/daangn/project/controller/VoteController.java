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
    public ResponseEntity<VoteSaveDto> createVote(@RequestBody VoteSaveDto voteSaveDto) throws IOException {
        voteService.save(voteSaveDto);
        return ResponseEntity.ok(voteSaveDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteResponseDto> showVote(@PathVariable Long id) {
        VoteResponseDto voteResponseDto = new VoteResponseDto(voteService.findById(id));
        voteService.getResultForVote(voteResponseDto);
        return ResponseEntity.ok(voteResponseDto);
    }

    @PutMapping
    public ResponseEntity<Long> updateVote(@RequestBody VoteUpdateDto voteUpdateDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteVote(@PathVariable String id) {
        return null;
    }

    @PostMapping("/participate")
    public ResponseEntity<Long> participateVote(@RequestBody VoteParticipateDto voteParticipateDto) {
        return null;
    }
}
