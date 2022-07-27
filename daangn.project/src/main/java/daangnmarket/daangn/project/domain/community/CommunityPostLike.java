package daangnmarket.daangn.project.domain.community;
import daangnmarket.daangn.project.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommunityPostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMUNITY_ID")
    private Community community;

    public CommunityPostLike(Community community, Member member) {
        this.community = community;
        this.member = member;
    }
}
