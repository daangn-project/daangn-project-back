package daangnmarket.daangn.project.dto;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.community.Community;
import daangnmarket.daangn.project.domain.community.CommunityComment;
import lombok.Data;

public class CommentDTO {

    @Data
    public static class ResponseDTO {
        private long id;
        private String writer;
        private String content;
        private Long parentCommentNum;
        private Long commentOrder;
        private Boolean isDeleted;
        private Long childCommentCount;

        public ResponseDTO(CommunityComment comment) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.writer = comment.getMember().getNickname();
            this.parentCommentNum = comment.getParentCommentNum();
            this.commentOrder = comment.getCommentOrder();
            this.childCommentCount = comment.getChildCommentCount();
            this.isDeleted = comment.getIsDeleted();
        }
    }


    @Data
    public static class SaveDTO {
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
}
