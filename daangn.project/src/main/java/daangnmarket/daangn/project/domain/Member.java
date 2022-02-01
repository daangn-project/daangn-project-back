package daangnmarket.daangn.project.domain;


import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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
    private List<ItemPost> itemPostList = new ArrayList<>();
}

