package daangnmarket.daangn.project.domain;

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

    private String name;
    private String path;
    private Long size;

    @ManyToOne
    @JoinColumn(name = "ITEM_POST_ID")
    private ItemPost itemPost;

    // 연관관계 메서드
    public void setItemPost(ItemPost itemPost) {
        this.itemPost = itemPost;
        if (!itemPost.getPhotoList().contains(this)) {
            itemPost.getPhotoList().add(this);
        }
    }

}
