package daangnmarket.daangn.project.dto.communitypost;

import daangnmarket.daangn.project.domain.CommunityCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityPostUpdateRequestDto {
    private String content;
    private CommunityCategory communityCategory;

    @Builder
    public CommunityPostUpdateRequestDto(String content, CommunityCategory communityCategory){

        this.communityCategory = communityCategory;
        this.content = content;
    }
}
