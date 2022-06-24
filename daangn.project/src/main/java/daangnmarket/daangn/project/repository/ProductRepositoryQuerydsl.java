package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.product.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryQuerydsl {
    List<Product> findByMemberId(Long memberId);
    List<Product> findByOrderByIdDescWithList(Pageable pageable);
    List<Product> findByIdLessThanOrderByIdDesc(Long id, Pageable pageable);
}
