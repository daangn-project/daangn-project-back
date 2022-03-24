package daangnmarket.daangn.project.domain.member;


import daangnmarket.daangn.project.domain.community.Community;
import daangnmarket.daangn.project.domain.community.CommunityComment;
import daangnmarket.daangn.project.domain.product.Product;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="profileImg")
    private String profileImg;

    @Column(name = "activated")
    private boolean activated;

    @ManyToMany
    @JoinTable(name = "MEMBER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "MEMBER_ID", referencedColumnName = "MEMBER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_NAME", referencedColumnName = "AUTHORITY_NAME")})
    private Set<Authority> authorities;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Product> productList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Community> communityList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<CommunityComment> commentList = new ArrayList<>();

}

