package daangnmarket.daangn.project.domain.product;

import daangnmarket.daangn.project.domain.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProductLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_POST_ID")
    private Product itemPost;
}
