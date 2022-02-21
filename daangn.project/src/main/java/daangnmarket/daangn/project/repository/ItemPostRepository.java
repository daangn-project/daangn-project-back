package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.ItemCategory;
import daangnmarket.daangn.project.domain.ItemPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemPostRepository extends JpaRepository<ItemPost, Long> {

    @Query(value = "select i from ItemPost i join fetch i.member where i.member.id = :member_id")
    List<ItemPost> findByMemberId(@Param(value="member_id") Long member_id);

    @Query(value = "select i from ItemPost i where i.itemCategory = :category")
    List<ItemPost> findByCategory(@Param(value="category") ItemCategory category);

}
