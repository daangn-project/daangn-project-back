package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.vote.VoteResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {

    @Query("select vr.member from VoteResult vr where vr.voteOption.id = :voteOptionId")
    List<Member> findAllMemberByVoteOptionId(@Param(value="voteOptionId") Long voteOptionId);
}
