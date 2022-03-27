package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.domain.product.Product;
import daangnmarket.daangn.project.domain.product.ProductLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    @Query(value = "select pl from ProductLike pl where pl.member = :memberId and pl.product = :productId")
    Optional<ProductLike> findByUserIdAndProductId(@Param(value = "memberId") Member memberId, @Param(value = "productId") Product productId);
}
