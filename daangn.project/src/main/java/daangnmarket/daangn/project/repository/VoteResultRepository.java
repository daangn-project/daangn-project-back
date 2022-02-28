package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.vote.VoteResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {
}
