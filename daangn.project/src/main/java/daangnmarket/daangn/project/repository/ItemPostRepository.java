package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.ItemCategory;
import daangnmarket.daangn.project.domain.ItemPost;
import daangnmarket.daangn.project.dto.ItemPostResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public interface ItemPostRepository extends JpaRepository<ItemPost, Long> {

    @Query(value = "select i from ItemPost i where i.id = :member_id")
    ItemPost findByMemberId(@Param(value="member_id") Long member_id);

    @Query(value = "select i from ItemPost i where i.itemCategory = :category")
    List<ItemPost> findByCategory(@Param(value="category") ItemCategory category);

}
