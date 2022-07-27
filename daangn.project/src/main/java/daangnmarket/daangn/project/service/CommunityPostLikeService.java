package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.community.Community;
import daangnmarket.daangn.project.domain.community.CommunityPostLike;
import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.repository.CommunityLikeRepository;
import daangnmarket.daangn.project.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityPostLikeService {
    private final CommunityLikeRepository communityLikeRepository;
    private final CommunityRepository communityRepository;

    public boolean addLike(Member member, Long communityId) {
        Community community = communityRepository.findById((communityId)).orElseThrow();

        // 중복 좋아요 방지
        if(isNotAlreadyLike(member, community)){
            communityLikeRepository.save(new CommunityPostLike(community, member));
            return true;
        }
        return false;
    }

    //사용자가 이미 좋아요 한 게시물인지 체크
    private boolean isNotAlreadyLike(Member member, Community community) {
        return communityLikeRepository.findByMemberAndCommunity(member, community).isEmpty();
    }
}
