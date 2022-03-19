package daangnmarket.daangn.project.domain.community;

import daangnmarket.daangn.project.converter.CommunityCategoryConverter;
import daangnmarket.daangn.project.converter.ProductCategoryConverter;
import daangnmarket.daangn.project.domain.BaseTimeEntity;
import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.domain.vote.Vote;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Community extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMUNITY_ID")
    private Long id;

    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Photo> photoList = new ArrayList<>();

    @Convert(converter = CommunityCategoryConverter.class)
    private CommunityCategory communityCategory;

    private Integer viewCount;

    @OneToOne
    @JoinColumn(name = "VOTE_ID")
    private Vote vote;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval=true)
    @Builder.Default
    private List<CommunityComment> commentList = new ArrayList<>();

    public void addPhoto(Photo photo){
        this.photoList.add(photo);
        if(photo.getCommunity() != this) photo.setCommunityPost(this);
    }

    // 연관관계 메서드
    public void setMember(Member member){
        this.member = member;
        member.getCommunityList().add(this);
    }
}
