package daangnmarket.daangn.project.domain;

import daangnmarket.daangn.project.domain.product.Product;
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
    private String path;
//    private String name;
//    private Long size;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ITEM_POST_ID")
    private Product itemPost;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COMMUNITY_POST_ID")
    private CommunityPost communityPost;

    // 연관관계 메서드
    public void setItemPost(Product itemPost) {
        this.itemPost = itemPost;
        if (!itemPost.getPhotoList().contains(this)) {
            itemPost.getPhotoList().add(this);
        }
    }

    // 동네 생활
    public void setCommunityPost(CommunityPost communityPost) {
        this.communityPost = communityPost;
        if(!communityPost.getPhotoList().contains(this)){
            communityPost.getPhotoList().add(this);
        }
    }

}
