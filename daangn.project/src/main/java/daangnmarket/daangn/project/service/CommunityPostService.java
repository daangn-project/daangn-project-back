package daangnmarket.daangn.project.service;


import daangnmarket.daangn.project.domain.CommunityPost;
import daangnmarket.daangn.project.dto.communitypost.CommunityPostResponseDto;
import daangnmarket.daangn.project.dto.communitypost.CommunityPostSaveDto;
import daangnmarket.daangn.project.repository.CommunityPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommunityPostService {
    private final CommunityPostRepository communityPostRepository;

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
    // 동네생활 게시물 생성
//    @Transactional
//    public Long save(CommunityPostSaveDto communityPostSaveDto){
//        return communityPostRepository.save(communityPostSaveDto.toEntity()).getId();
//    }

//    @Transactional
//    public Long update(Long id, CommunityPostUpdateRequestDto communityPostUpdateRequestDto) {
//        CommunityPost communityPost = communityPostRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 동네생활이 없습니다. id="+id));
//
//        communityPost.update(communityPostUpdateRequestDto.getContent());
//        return id;
//    }

}
