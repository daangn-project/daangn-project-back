package daangnmarket.daangn.project.domain;

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
}
