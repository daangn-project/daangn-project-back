package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.community.CommunityCategory;
import daangnmarket.daangn.project.domain.community.Community;
import daangnmarket.daangn.project.domain.product.Product;
import daangnmarket.daangn.project.domain.product.ProductCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findByCommunityCategory( CommunityCategory category);

    List<Community> findByMemberId(Long memberId);

    @Query("select p from Community p order by p.id desc")
    List<Community> findByOrderByIdDescWithList(Pageable pageable);

    List<Community> findByIdLessThanOrderByIdDesc(Long id, Pageable pageable);
}
