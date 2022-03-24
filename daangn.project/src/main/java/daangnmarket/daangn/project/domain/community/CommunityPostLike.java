package daangnmarket.daangn.project.domain.community;

import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.domain.product.Product;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CommunityPostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMUNITY_POST_ID")
    private Product itemPost;
}
