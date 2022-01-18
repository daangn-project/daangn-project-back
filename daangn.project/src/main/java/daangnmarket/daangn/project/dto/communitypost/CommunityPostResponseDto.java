package daangnmarket.daangn.project.dto.communitypost;

import daangnmarket.daangn.project.domain.CommunityPost;
import lombok.Getter;

@Getter
public class CommunityPostResponseDto {
    private Long id;
    private String content;
    private String member;
    private String communityPostCategory;

    public CommunityPostResponseDto(CommunityPost entity){
        this.id = entity.getId();
        this.content = entity.getContent();
        this.member = String.valueOf(entity.getMember());
        this.communityPostCategory = String.valueOf(entity.getCommunityCategory());
    }
}
