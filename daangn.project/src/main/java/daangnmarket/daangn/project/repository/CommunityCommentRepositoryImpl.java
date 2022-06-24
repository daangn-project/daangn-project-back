package daangnmarket.daangn.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static daangnmarket.daangn.project.domain.community.QCommunityComment.communityComment;

@RequiredArgsConstructor
@Repository
public class CommunityCommentRepositoryImpl implements CommunityCommentRepositoryQuerydsl{
    private final JPAQueryFactory queryFactory;

    @Override
    public Long getLastCommentOrder() {
        return queryFactory
                .select(communityComment.commentOrder)
                .from(communityComment)
                .orderBy(communityComment.commentOrder.desc())
                .fetchFirst();
    }
}
