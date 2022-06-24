package daangnmarket.daangn.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import daangnmarket.daangn.project.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static daangnmarket.daangn.project.domain.member.QAuthority.authority;
import static daangnmarket.daangn.project.domain.member.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryQuerydsl{
    private final JPAQueryFactory queryFactory;

    @Override
    public Boolean existsByUsername(String username) {
        return queryFactory
                .selectFrom(member)
                .where(member.username.eq(username))
                .fetchFirst() != null;
    }

    @Override
    public Member findOneWithAuthoritiesByUsername(String username) {
        return queryFactory
                .selectFrom(member)
                .join(member.authorities, authority).fetchJoin()
                .where(member.username.eq(username))
                .fetchOne();
    }
}
