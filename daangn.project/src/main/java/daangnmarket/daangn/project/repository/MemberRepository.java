package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryQuerydsl{
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByUsername(String username);
    @Modifying(clearAutomatically = true)
    @Query("update Member m SET m.nickname = :nickname where m.id = :id")
    int updateUserNickname(@Param(value="nickname") String nickname, @Param(value="id")Long id);

}
