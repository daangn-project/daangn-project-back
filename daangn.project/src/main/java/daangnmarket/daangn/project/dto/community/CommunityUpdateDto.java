package daangnmarket.daangn.project.dto.community;

import daangnmarket.daangn.project.domain.community.CommunityCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityUpdateDto {
    private String content;
    private CommunityCategory communityCategory;

    @Builder
    public CommunityUpdateDto(String content, CommunityCategory communityCategory){

        this.communityCategory = communityCategory;
        this.content = content;
    }
}
