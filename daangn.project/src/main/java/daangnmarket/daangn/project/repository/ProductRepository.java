package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.product.Product;
import daangnmarket.daangn.project.domain.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryQuerydsl {
    List<Product> findByProductCategory(ProductCategory productCategory);

}
