package daangnmarket.daangn.project.dto.vote;

import daangnmarket.daangn.project.domain.vote.Vote;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class VoteResponseDto {
    private Long id;
    private String title;
    private String description;
    private String writer;
    private List<VoteOptionResponseDto> voteOptionResponseDtos;

    public VoteResponseDto(Vote vote){
        this.id = vote.getId();
        this.title = vote.getTitle();
        this.description = vote.getDescription();
        this.writer = vote.getMember().getNickname();
        this.voteOptionResponseDtos = vote.getVoteOptionList().stream().map(
                VoteOptionResponseDto::new).collect(Collectors.toList());
    }
}
