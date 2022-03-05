package daangnmarket.daangn.project.service;


import daangnmarket.daangn.project.domain.*;

import daangnmarket.daangn.project.domain.community.Community;
import daangnmarket.daangn.project.domain.community.CommunityCategory;
import daangnmarket.daangn.project.dto.community.CommunityResponseDto;
import daangnmarket.daangn.project.dto.community.CommunitySaveDto;
import daangnmarket.daangn.project.handler.S3Uploader;
import daangnmarket.daangn.project.repository.CommunityRepository;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.repository.PhotoRepository;
import daangnmarket.daangn.project.vo.CommunityPostFileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    // 모든 동네생활 게시물 조회
    @Transactional(readOnly = true)
    public List<CommunityResponseDto> findAll(){
        return communityRepository.findAll().stream().map(CommunityResponseDto::new).collect(Collectors.toList());
    }

    // 동네생활 조회 by Id
    public CommunityResponseDto findById(Long id){
        Community entity = communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 동네 생활이 없습니다. id="+id));
        return new CommunityResponseDto(entity);
    }

    // 동네생활 조회 by category
    @Transactional(readOnly = true)
    public List<CommunityResponseDto> findByCategory(String category) {
        CommunityCategory communityCategory = CommunityCategory.valueOf(category);
        List<Community> byCategory = communityRepository.findByCategory(communityCategory);
        return byCategory.stream().map(CommunityResponseDto::new).collect(Collectors.toList());
    }

    // 생성
    public void save(CommunitySaveDto communityPostSaveDto, List<MultipartFile> files) throws IOException {

        Member member = memberRepository.findByNickname(communityPostSaveDto.getWriter()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        Community community = Community.builder()

                .member(member)
                .title(communityPostSaveDto.getTitle())
                .description(communityPostSaveDto.getDescription())
                .communityCategory(communityPostSaveDto.getCommunityCategory())
                .viewCount(0)
                .build();

        files.forEach((f) -> {
            try {
                String S3Url = s3Uploader.upload(f, "static");
                community.addPhoto(photoRepository.save(Photo.builder().path(S3Url).build()));
                communityPostSaveDto.getPhotoList().add(S3Url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        community.setMember(member);
        communityRepository.save(community);
    }

    // 동네 생활 수정
    @Transactional
    public CommunitySaveDto update(Long id, CommunityPostFileVO communityPostFileVO) {
        Community communityPost = communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 동네생활이 없습니다. id="+id));

        return CommunitySaveDto.builder()
                .writer(communityPost.getMember().getNickname())
                .title(communityPostFileVO.getTitle())
                .description(communityPostFileVO.getDescription())
                .communityCategory(communityPostFileVO.getCommunityCategory())
                .build();
    }

    // 동네 생활 삭제
    public void delete(Long id) throws IllegalArgumentException {
        Community community = communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 동네생활이 없습니다. id="+id));
        communityRepository.delete(community);
    }


}
