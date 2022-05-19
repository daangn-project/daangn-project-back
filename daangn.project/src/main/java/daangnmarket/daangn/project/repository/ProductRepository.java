package daangnmarket.daangn.project.repository;
import daangnmarket.daangn.project.domain.product.ProductCategory;
import daangnmarket.daangn.project.domain.product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByMemberId(Long memberId);

    List<Product> findByProductCategory(ProductCategory category);

    @Query("select p from Product p order by p.id desc")
    List<Product> findByOrderByIdDescWithList(Pageable pageable);

    List<Product> findByIdLessThanOrderByIdDesc(Long id, Pageable pageable);
}
