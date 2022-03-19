package daangnmarket.daangn.project.domain.community;

import daangnmarket.daangn.project.domain.BaseTimeEntity;
import daangnmarket.daangn.project.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@Builder
@AllArgsConstructor
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

    private Long parentCommentNum; // 자신이 속한 원댓글의 번호
    private Long commentOrder; // 자신의 댓글 번호
    private Long childCommentCount; // 자식 댓글의 개수
    private Boolean isDeleted; // 댓글 삭제 여부
    private Integer likeCount; // 댓글 좋아요
    private Integer hateCount; // 댓글 싫어요

    // 연관관계 메서드
    public void setMemberAndCommunity(Member member, Community community){
        this.member = member;
        member.getCommentList().add(this);
        this.community = community;
        community.getCommentList().add(this);
    }

    public void setDeleted(){
        this.isDeleted = true;
    }
    public void plusChildCommentCount(){this.childCommentCount++;}
    public void minusChildCommentCount(){this.childCommentCount--;}
}
