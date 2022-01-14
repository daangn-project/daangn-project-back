package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long>{

    @Modifying(clearAutomatically = true)
    @Query("update Member m SET m.nickname = :nickname where m.id = :id")
    int updateUserNickname(@Param(value="nickname") String nickname, @Param(value="id")Long id);

    @Query("select m from Member m where m.id = :id")
    Member findByPostId(@Param(value="id") Long id);
}
