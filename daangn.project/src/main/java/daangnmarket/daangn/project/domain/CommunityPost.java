package daangnmarket.daangn.project.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityPost extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMUNITY_POST_ID")
    private Long id;

    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "communityPost", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default // 빌더 패턴으로 생성 시 기본값으로 비어있는 리스트 생성
    private List<Photo> photoList = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    private CommunityCategory communityCategory;

    private Integer viewCount;

//    public void update(String description){
//        this.description = description;
//    }

    @Builder
    public CommunityPost (String description, Member member, CommunityCategory communityCategory){
        this.description = description;
        this.member = member;
        this.communityCategory = communityCategory;
    }
}
