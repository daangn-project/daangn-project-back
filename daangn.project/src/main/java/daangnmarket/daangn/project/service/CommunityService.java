package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.*;
import daangnmarket.daangn.project.domain.community.Community;
import daangnmarket.daangn.project.domain.community.CommunityCategory;
import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.domain.product.Product;
import daangnmarket.daangn.project.domain.vote.Vote;
import daangnmarket.daangn.project.dto.CommunityDTO;
import daangnmarket.daangn.project.dto.ProductDTO;
import daangnmarket.daangn.project.dto.VoteDTO;
import daangnmarket.daangn.project.handler.S3Uploader;
import daangnmarket.daangn.project.repository.CommunityRepository;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final S3Uploader s3Uploader;
    private final MemberRepository memberRepository;
    private final PhotoRepository photoRepository;
    private final VoteService voteService;

    // 모든 동네생활 게시물 조회
    @Transactional(readOnly = true)
    public List<CommunityDTO.ResponseDTO> findCommunityByPage(Long cursor, Pageable pageable){
        return getCommunityList(cursor, pageable)
                .stream().map(CommunityDTO.ResponseDTO::new).collect(Collectors.toList());
    }



    // 동네생활 조회 by Id
    public CommunityDTO.ResponseDTO findById(Long id){
        Community entity = communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 동네 생활이 없습니다. id="+id));
        CommunityDTO.ResponseDTO returnDto = new CommunityDTO.ResponseDTO(entity);
        voteService.getResultOfVote(returnDto.getVoteResponseDto());
        return returnDto;
    }

    // 동네생활 조회 by category
    @Transactional(readOnly = true)
    public List<CommunityDTO.ResponseDTO> findByCategory(String category) {
        CommunityCategory communityCategory = CommunityCategory.valueOf(category);
        List<Community> byCategory = communityRepository.findByCommunityCategory(communityCategory);
        return byCategory.stream().map(CommunityDTO.ResponseDTO::new).collect(Collectors.toList());
    }

    // 생성
    public void save(CommunityDTO.SaveDTO communitySaveDto) throws IOException {
        Member member = memberRepository.findByNickname(communitySaveDto.getWriter()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Vote createdVote = null;
        if(communitySaveDto.getIsVoteArticle())
            createdVote = voteService.save(new VoteDTO.SaveDTO(communitySaveDto.getIsMultipleVote(), communitySaveDto.getVoteOptions()));
        Community community = Community.builder()
                .member(member)
                .title(communitySaveDto.getTitle())
                .description(communitySaveDto.getDescription())
                .communityCategory(communitySaveDto.getCommunityCategory())
                .vote(createdVote)
                .build();

        communitySaveDto.getImages().forEach((f) -> {
            try {
                String S3Url = s3Uploader.upload(f, "static");
                community.addPhoto(photoRepository.save(Photo.builder().path(S3Url).build()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        community.setMember(member);
        communityRepository.save(community);
    }

    // 동네 생활 수정
//    @Transactional
//    public CommunitySaveDto update(Long id, CommunityPostFileVO communityPostFileVO) {
//        Community communityPost = communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 동네생활이 없습니다. id="+id));
//
//        return CommunitySaveDto.builder()
//                .writer(communityPost.getMember().getNickname())
//                .title(communityPostFileVO.getTitle())
//                .description(communityPostFileVO.getDescription())
//                .communityCategory(communityPostFileVO.getCommunityCategory())
//                .build();
//    }

    // 동네 생활 삭제
    public void delete(Long id) throws IllegalArgumentException {
        Community community = communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 동네생활이 없습니다. id="+id));
        communityRepository.delete(community);
    }


    // 페이징 처리를 위한 메서드
    private List<Community> getCommunityList(Long id, Pageable page) {
        return id.equals(0L)
                ? communityRepository.findByOrderByIdDescWithList(page)
                : communityRepository.findByIdLessThanOrderByIdDesc(id, page);
    }
}
