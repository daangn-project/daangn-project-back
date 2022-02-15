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
// itemPost에서 파일 처리
public void addPhoto(Photo photo){
    this.photoList.add(photo);
    // 게시글에 파일이 저장되어있지 않은 경우
    if(photo.getCommunityPost() != this) photo.setCommunityPost(this);
}

    // 연관관계 메서드
    public void setMember(Member member){
        this.member = member;
        member.getCommunityPostList().add(this);
    }
    @Builder
    public CommunityPost (String description, Member member, CommunityCategory communityCategory){
        this.description = description;
        this.member = member;
        this.communityCategory = communityCategory;
    }
}
