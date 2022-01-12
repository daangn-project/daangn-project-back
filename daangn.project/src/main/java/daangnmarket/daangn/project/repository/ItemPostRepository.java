package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.ItemPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemPostRepository extends JpaRepository<ItemPost, Long> {

    @Query(value = "select i from ItemPost i where i.id = :member_id")
    ItemPost findByMemberId(@Param(value="member_id") Long member_id);
}
