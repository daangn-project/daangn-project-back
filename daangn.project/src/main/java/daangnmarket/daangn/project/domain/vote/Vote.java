package daangnmarket.daangn.project.domain.vote;

import daangnmarket.daangn.project.domain.Member;
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

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String title;

    @Column(nullable = true)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vote", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<VoteOption> voteOptionList = new ArrayList<>();

    // 24시간 뒤 시간을 default로 둔다.
    @Column(columnDefinition = "")
    private Date expirationTime;

    // 연관관계 메서드
    public void setMember(Member member){
        this.member = member;
        member.getVoteList().add(this);
    }
}
