package daangnmarket.daangn.project.dto.vote;

import lombok.Data;
import java.util.List;

@Data
public class VoteSaveDto {
    private String writer;
    private String title;
    private String description;
    private List<VoteOptionCreateDto> voteOptionCreateDtoList;
}
