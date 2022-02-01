package daangnmarket.daangn.project.domain;

import daangnmarket.daangn.project.converter.ItemCategoryConverter;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_POST_ID")
    private Long id;

    private String title;

    @OneToMany(mappedBy = "itemPost", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default // 빌더 패턴으로 생성 시 기본값으로 비어있는 리스트 생성
    private List<Photo> photoList = new ArrayList<>();

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Convert(converter = ItemCategoryConverter.class)
    private ItemCategory itemCategory;

    private Integer viewCount;

    private Integer price;

    // itemPost에서 파일 처리
    public void addPhoto(Photo photo){
        this.photoList.add(photo);
        // 게시글에 파일이 저장되어있지 않은 경우
        if(photo.getItemPost() != this) photo.setItemPost(this);
    }
}
