package daangnmarket.daangn.project.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    private String password;

    private String email;

    private String profileImg;

//    @Enumerated(EnumType.STRING)
//    private Role role;

    private String auth;

}

