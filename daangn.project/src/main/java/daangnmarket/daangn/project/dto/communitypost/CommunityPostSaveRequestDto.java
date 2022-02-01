package daangnmarket.daangn.project.dto.communitypost;

import daangnmarket.daangn.project.domain.CommunityCategory;
import daangnmarket.daangn.project.domain.CommunityPost;
import daangnmarket.daangn.project.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityPostSaveRequestDto {
    private String content;
    private Member member;
    private CommunityCategory communityCategory;

    @Builder
    public CommunityPostSaveRequestDto(CommunityCategory communityCategory, String content, Member member) {
        this.member = member;
        this.content = content;
        this.communityCategory = communityCategory;
    }

    public CommunityPost toEntity(){
        return CommunityPost.builder()
                .content(content)
                .member(member)
                .communityCategory(communityCategory)
                .build();
    }
}
