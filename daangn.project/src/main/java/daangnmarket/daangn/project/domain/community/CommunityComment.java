package daangnmarket.daangn.project.domain.community;

import daangnmarket.daangn.project.domain.BaseTimeEntity;
import daangnmarket.daangn.project.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CommunityComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMUNITY_ID")
    private Community community; // 게시판 종류

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member; // 댓글을 작성한 유저

    private String content; // 댓글 내용

    private long parentCommentNum; // 자신이 속한 원댓글의 번호

    private long commentNum; // 자신의 댓글 번호

    private long childCommentCount; // 자식 댓글의 개수

    private boolean isDeleted; // 댓글 상태 (죽었는지 살았는지)

    private Integer likeCount; // 댓글 좋아요카운트

    @Builder
    public CommunityComment createComment(Member member,Community community, String content, long parentCommentNum, long commentNum){
        this.member = member;
        this.community = community;
        this.content = content;
        this.likeCount = 0;
        this.childCommentCount = 0;
        this.isDeleted = false;
        this.parentCommentNum = parentCommentNum;
        this.commentNum = commentNum;
        this.member.getCommentList().add(this);
        this.community.getCommentList().add(this);
        return this;
    }
}
