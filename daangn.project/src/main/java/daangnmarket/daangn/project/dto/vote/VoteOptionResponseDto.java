package daangnmarket.daangn.project.dto.vote;

import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.domain.vote.VoteOption;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class VoteOptionResponseDto {
    private Long id;
    private String content;
    private List<String> imgUrls;

    public VoteOptionResponseDto(VoteOption voteOption){
        this.id = voteOption.getId();
        this.content = voteOption.getContent();
        this.imgUrls = voteOption.getPhotoList().stream().map(Photo::getPath).collect(Collectors.toList());

    }
}
