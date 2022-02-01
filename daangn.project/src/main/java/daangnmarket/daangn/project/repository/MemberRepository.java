package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{

    public Optional<Member> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("update Member m SET m.nickname = :nickname where m.id = :id")
    int updateUserNickname(@Param(value="nickname") String nickname, @Param(value="id")Long id);

    @Query("select m from Member m where m.nickname = :nickname")
    Member findByNickname(@Param(value="nickname") String writer);

    @Query("select m from Member m where m.username = :username")
    Optional<Member> findByUsername(@Param(value="username") String username);
}
