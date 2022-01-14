package daangnmarket.daangn.project.domain;


import lombok.*;

import javax.persistence.*;

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



}

