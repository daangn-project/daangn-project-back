package daangnmarket.daangn.project.domain;

import daangnmarket.daangn.project.domain.community.Community;
import daangnmarket.daangn.project.domain.product.Product;
import daangnmarket.daangn.project.domain.vote.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PHOTO_ID")
    private Long id;

    @Column(name="path", nullable = false)
    private String path;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COMMUNITY_ID")
    private Community community;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VOTE_ID")
    private Vote vote;

    // 연관관계 메서드
    public void setProduct(Product product) {
        this.product = product;
        if (!product.getPhotoList().contains(this)) {
            product.getPhotoList().add(this);
        }
    }

    // 동네 생활
    public void setCommunityPost(Community community) {
        this.community = community;
        if(!community.getPhotoList().contains(this)){
            community.getPhotoList().add(this);
        }
    }

}
