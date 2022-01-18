package daangnmarket.daangn.project.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMUNITY_POST_ID")
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Enumerated(EnumType.STRING)
    private CommunityCategory communityCategory;

    private Integer viewCount;

    public void update(String content){
        this.content = content;
    }

    @Builder
    public CommunityPost (String content, Member member, CommunityCategory communityCategory){
        this.content = content;
        this.member = member;
        this.communityCategory = communityCategory;
    }
}
