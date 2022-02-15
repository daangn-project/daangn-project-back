package daangnmarket.daangn.project.service;


import daangnmarket.daangn.project.domain.*;
import daangnmarket.daangn.project.dto.ItemPostResponseDto;
import daangnmarket.daangn.project.dto.ItemPostSaveDto;
import daangnmarket.daangn.project.dto.communitypost.CommunityPostResponseDto;
import daangnmarket.daangn.project.dto.communitypost.CommunityPostSaveDto;
import daangnmarket.daangn.project.handler.S3Uploader;
import daangnmarket.daangn.project.repository.CommunityPostRepository;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommunityPostService {
    private final CommunityPostRepository communityPostRepository;
    private final S3Uploader s3Uploader;
    private final MemberRepository memberRepository;
    private final PhotoRepository photoRepository;

    // 모든 동네생활 게시물 조회
    @Transactional(readOnly = true)
    public List<CommunityPostResponseDto> findAll(){
        return communityPostRepository.findAll().stream().map(CommunityPostResponseDto::new).collect(Collectors.toList());
    }

    // 동네생활 조회 by Id
    public CommunityPostResponseDto findById(Long id){
        CommunityPost entity = communityPostRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 동네 생활이 없습니다. id="+id));
        return new CommunityPostResponseDto(entity);
    }

    // 동네생활 조회 by category
    @Transactional(readOnly = true)
    public List<CommunityPostResponseDto> findByCategory(String category) {
        CommunityCategory communityCategory = CommunityCategory.valueOf(category);
        List<CommunityPost> byCategory = communityPostRepository.findByCategory(communityCategory);
        return byCategory.stream().map(CommunityPostResponseDto::new).collect(Collectors.toList());
    }

    // 생성
    public void save(CommunityPostSaveDto communityPostSaveDto, List<MultipartFile> files) throws IOException {
        Member member = memberRepository.findByNickname(communityPostSaveDto.getWriter());
        CommunityPost communityPost = CommunityPost.builder()
                .member(member)
                .title(communityPostSaveDto.getTitle())
                .description(communityPostSaveDto.getDescription())
                .communityCategory(communityPostSaveDto.getCommunityCategory())
                .viewCount(0)
                .build();

        files.forEach((f) -> {
            try {
                String S3Url = s3Uploader.upload(f, "static");
                communityPost.addPhoto(photoRepository.save(Photo.builder().path(S3Url).build()));
                communityPostSaveDto.getPhotoList().add(S3Url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        communityPost.setMember(member);
        communityPostRepository.save(communityPost);
    }

//    @Transactional
//    public Long update(Long id, CommunityPostUpdateRequestDto communityPostUpdateRequestDto) {
//        CommunityPost communityPost = communityPostRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 동네생활이 없습니다. id="+id));
//
//        communityPost.update(communityPostUpdateRequestDto.getContent());
//        return id;
//    }

}
