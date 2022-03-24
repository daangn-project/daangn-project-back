package daangnmarket.daangn.project.domain.product;

import daangnmarket.daangn.project.converter.ProductCategoryConverter;
import daangnmarket.daangn.project.domain.BaseTimeEntity;
import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.domain.Photo;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity @Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default // 빌더 패턴으로 생성 시 기본값으로 비어있는 리스트 생성
    private List<Photo> photoList = new ArrayList<>();

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Convert(converter = ProductCategoryConverter.class)
    private ProductCategory productCategory;

    private Integer price;

    public void addPhoto(Photo photo){
        this.photoList.add(photo);
        // 게시글에 파일이 저장되어있지 않은 경우
        if(photo.getProduct() != this) photo.setProduct(this);
    }

    // 연관관계 메서드
    public void setMember(Member member){
        this.member = member;
        member.getProductList().add(this);
    }
}
