package daangnmarket.daangn.project.dto.comment;

import daangnmarket.daangn.project.domain.community.CommunityComment;
import lombok.Data;

@Data
public class CommentResponseDto {
    private long id;
    private String writer;
    private String content;
    private Long parentCommentNum;
    private Long commentOrder;
    private Boolean isDeleted;
    private Long childCommentCount;

    public CommentResponseDto(CommunityComment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.writer = comment.getMember().getNickname();
        this.parentCommentNum = comment.getParentCommentNum();
        this.commentOrder = comment.getCommentOrder();
        this.childCommentCount = comment.getChildCommentCount();
        this.isDeleted = comment.getIsDeleted();
    }
}
