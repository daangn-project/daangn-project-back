package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.vote.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteOptionRepository extends JpaRepository<VoteOption, Long> {
}
