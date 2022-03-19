package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.community.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {

    @Query("SELECT coalesce(max(cmt.commentOrder), 0) FROM CommunityComment cmt")
    Long getLastCommentOrder();


    @Query("SELECT cmt FROM CommunityComment cmt where cmt.commentOrder = :order")
    CommunityComment findByCommentOrder(@Param(value = "order") Long commentOrder);


    @Query("SELECT distinct c.community.id from CommunityComment as c where c.member.id = :member_id")
    List<Long> findByMemberId(@Param(value = "member_id") Long member_id);

}
