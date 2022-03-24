package daangnmarket.daangn.project.dto.community;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.community.Community;
import daangnmarket.daangn.project.domain.community.CommunityComment;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CommunityCommentDto {
    private Long commentId;
    private Community community;
    private Member member;
    private String content;
    private long parentCommentNum;
    private long childCommentCount;
    private boolean isDeleted;
}
