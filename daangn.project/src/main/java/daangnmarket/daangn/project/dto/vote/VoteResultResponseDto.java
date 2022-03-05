package daangnmarket.daangn.project.dto.vote;

import lombok.Data;

import java.util.List;

@Data
public class VoteResultResponseDto {
    private Long voteOptionId;
    private List<Long> memberIdList;


    public VoteResultResponseDto(Long id, List<Long> memberIdList){
        this.voteOptionId = id;
        this.memberIdList = memberIdList;
    }
}
