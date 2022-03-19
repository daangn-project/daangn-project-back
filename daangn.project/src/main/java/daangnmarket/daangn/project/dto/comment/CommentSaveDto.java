package daangnmarket.daangn.project.dto.comment;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.community.Community;
import daangnmarket.daangn.project.domain.community.CommunityComment;
import lombok.Data;

@Data
public class CommentSaveDto {
    private String writer;
    private String content;
    private Long communityId;
    private Long parentCommentNum;
    private Long commentOrder;
    private Boolean isDeleted;

    public CommunityComment toEntity(Member m, Community c){
        return CommunityComment.builder()
                .member(m)
                .community(c)
                .content(content)
                .likeCount(0)
                .hateCount(0)
                .parentCommentNum(parentCommentNum)
                .commentOrder(commentOrder)
                .childCommentCount(0L)
                .isDeleted(false)
                .build();
    }
}
