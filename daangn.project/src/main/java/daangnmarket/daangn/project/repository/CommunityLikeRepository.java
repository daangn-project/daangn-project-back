package daangnmarket.daangn.project.repository;

import daangnmarket.daangn.project.domain.community.Community;
import daangnmarket.daangn.project.domain.community.CommunityPostLike;
import daangnmarket.daangn.project.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityLikeRepository extends JpaRepository<CommunityPostLike, Long> {
    Optional<CommunityPostLike> findByMemberAndCommunity(Member member, Community community);

}
