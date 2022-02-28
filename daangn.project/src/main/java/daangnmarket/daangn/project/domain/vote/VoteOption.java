package daangnmarket.daangn.project.domain.vote;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VOTE_OPTION_ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VOTE_ID")
    private Vote vote;

    private String content;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "voteOption", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Photo> photoList = new ArrayList<>();

    public void addPhoto(Photo photo){
        this.photoList.add(photo);
        // 게시글에 파일이 저장되어있지 않은 경우
        if(photo.getVoteOption() != this) photo.setVoteOption(this);
    }

    // 연관관계 메서드
    public void setVote(Vote vote){
        this.vote = vote;
        vote.getVoteOptionList().add(this);
    }

}
