package daangnmarket.daangn.project.repository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import daangnmarket.daangn.project.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static daangnmarket.daangn.project.domain.member.QMember.member;
import static daangnmarket.daangn.project.domain.product.QProduct.product;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepositoryQuerydsl {
    private final JPAQueryFactory queryFactory;

    public List<Product> findByMemberId(Long memberId){
        return queryFactory
                .selectFrom(product)
                .join(product.member, member).fetchJoin()
                .where(product.member.id.eq(memberId))
                .fetch();
    }

    public List<Product> findByOrderByIdDescWithList(Pageable pageable) {
        return queryFactory
                .selectFrom(product)
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<Product> findByIdLessThanOrderByIdDesc(Long id, Pageable pageable) {
        return queryFactory
                .selectFrom(product)
                .orderBy(product.id.desc())
                .where(product.id.lt(id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
