package daangnmarket.daangn.project.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class VoteOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VOTE_OPTION_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "VOTE_ID")
    private VoteOption voteOptions;


    private String voteOptionName;

    private Integer voteTotalCount;

    @OneToMany
    private List<Member> participateMember = new ArrayList<>();

}
