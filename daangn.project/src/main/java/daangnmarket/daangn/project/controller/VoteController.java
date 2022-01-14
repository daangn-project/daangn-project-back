package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.dto.VoteParticipateDto;
import daangnmarket.daangn.project.dto.VoteSaveDto;
import daangnmarket.daangn.project.dto.VoteUpdateDto;
import daangnmarket.daangn.project.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/votes")
public class VoteController {
    private final VoteService voteService;

    @GetMapping("/{id}")
    public ResponseEntity<Long> showVote(@PathVariable String id) {
        return null;
    }


    @PostMapping
    public ResponseEntity<Long> createVote(@RequestBody VoteSaveDto voteSaveDto){
        return null;
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
