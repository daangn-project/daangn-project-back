package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.vote.VoteResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {
<<<<<<< Updated upstream
=======

    @Query("select vr.member from VoteResult vr where vr.voteOption.id = :voteOptionId")
    List<Member> findAllMemberByVoteOptionId(@Param(value="voteOptionId") Long voteOptionId);

    @Query("select vr from VoteResult vr where vr.id = :voteId")
    List<VoteResult> findByVoteId(@Param(value="voteId") Long voteId);
>>>>>>> Stashed changes
}
