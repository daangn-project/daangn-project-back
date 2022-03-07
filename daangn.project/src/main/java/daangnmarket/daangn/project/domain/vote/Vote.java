package daangnmarket.daangn.project.domain.vote;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VOTE_ID")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vote", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<VoteOption> voteOptionList = new ArrayList<>();

    @Column(columnDefinition = "")
    private Date expirationTime;

    private Boolean isMultipleVote;
}
