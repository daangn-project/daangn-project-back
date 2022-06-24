package daangnmarket.daangn.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import daangnmarket.daangn.project.domain.community.Community;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static daangnmarket.daangn.project.domain.community.QCommunity.community;

@RequiredArgsConstructor
@Repository
public class CommunityRepositoryImpl implements CommunityRepositoryQuerydsl{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Community> findByOrderByIdDescWithList(Pageable pageable) {
        return queryFactory
                .selectFrom(community)
                .orderBy(community.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }


}
