package daangnmarket.daangn.project.dto.vote;

import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.domain.vote.Vote;
import daangnmarket.daangn.project.service.VoteService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class VoteResponseDto {
    private boolean isMultipleVote;
    private List<VoteOptionResponseDto> voteOptionResponseDtos;
    private List<VoteResultResponseDto> voteResultResponseDtos;

    public VoteResponseDto(Vote vote){
        this.isMultipleVote = vote.getIsMultipleVote();
        this.voteOptionResponseDtos = vote.getVoteOptionList().stream().map(
                VoteOptionResponseDto::new).collect(Collectors.toList());
    }
}
