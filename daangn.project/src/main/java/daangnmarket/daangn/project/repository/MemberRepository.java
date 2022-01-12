package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.ItemPost;
import daangnmarket.daangn.project.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "select m from Member m where m.id = :post_id")
    Member findByPostId(@Param(value="post_id") Long post_id);
}
