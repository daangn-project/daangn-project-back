package daangnmarket.daangn.project.dto.vote;

import lombok.Data;
import java.util.List;

@Data
public class VoteSaveDto {
    private Boolean isMultipleVote;
    private List<String> voteOptions;

    public VoteSaveDto(Boolean isMultipleVote, List<String> voteOptions) {
        this.isMultipleVote = isMultipleVote;
        this.voteOptions = voteOptions;
    }
}
