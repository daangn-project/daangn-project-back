package daangnmarket.daangn.project.domain;


import daangnmarket.daangn.project.domain.community.Community;
import daangnmarket.daangn.project.domain.community.CommunityComment;
import daangnmarket.daangn.project.domain.product.Product;
import daangnmarket.daangn.project.domain.vote.Vote;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    private String nickname;

    private String password;

    private String email;

    private String profileImg;

//    @Enumerated(EnumType.STRING)
//    private Role role;

    private String auth;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Product> productList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Community> communityList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Vote> voteList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CommunityComment> commentList = new ArrayList<>();


}

