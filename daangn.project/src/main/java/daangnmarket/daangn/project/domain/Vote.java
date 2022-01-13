package daangnmarket.daangn.project.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VOTE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "MEMBER_ID")
    private Member voteOwner;

    private String voteName;

    @Column(nullable = true)
    private String voteDescription;

    @OneToMany
    @JoinColumn(name = "VOTE_OPTION_ID")
    private List<VoteOption> voteOptions = new ArrayList<>();

    // 24시간 뒤 시간을 default로 둔다.
    @Column(columnDefinition = "")
    private Date voteTime;

}
