package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.product.ProductCategory;
import daangnmarket.daangn.project.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);

    @Query(value = "select i from Product i join fetch i.member where i.member.id = :member_id")
    List<Product> findByMemberId(@Param(value="member_id") Long member_id);

    @Query(value = "select i from Product i where i.productCategory = :category")
    List<Product> findByCategory(@Param(value="category") ProductCategory category);

}
