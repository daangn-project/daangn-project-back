package daangnmarket.daangn.project.dto.vote;

import lombok.Data;

import java.util.List;

@Data
public class VoteResultResponseDto {
    private Long voteOptionId;
    private List<Long> memberId;

    public VoteResultResponseDto(Long id, List<Long> memberId){
        this.voteOptionId = id;
        this.memberId = memberId;
    }
}
