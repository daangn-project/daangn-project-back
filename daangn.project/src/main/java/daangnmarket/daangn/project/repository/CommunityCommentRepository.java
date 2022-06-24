package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.community.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long>, CommunityCommentRepositoryQuerydsl {
    CommunityComment findByCommentOrder(Long commentOrder);
    List<Long> findByMemberId( Long member_id);

}
