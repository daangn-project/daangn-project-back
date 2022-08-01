package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.member.Member;

import java.util.Optional;

public interface MemberRepositoryQuerydsl {
    Boolean existsByUsername(String username);
    Member findOneWithAuthoritiesByUsername(String username);
    Member findMemberById(Long id);
}
