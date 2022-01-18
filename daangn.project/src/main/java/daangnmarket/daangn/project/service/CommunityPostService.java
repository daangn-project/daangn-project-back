package daangnmarket.daangn.project.service;


import daangnmarket.daangn.project.domain.CommunityPost;
import daangnmarket.daangn.project.dto.communitypost.CommunityPostResponseDto;
import daangnmarket.daangn.project.dto.communitypost.CommunityPostSaveRequestDto;
import daangnmarket.daangn.project.dto.communitypost.CommunityPostUpdateRequestDto;
import daangnmarket.daangn.project.repository.CommunityPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommunityPostService {
    private final CommunityPostRepository communityPostRepository;

    @Transactional
    public Long save(CommunityPostSaveRequestDto communityPostSaveDto){
        return communityPostRepository.save(communityPostSaveDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, CommunityPostUpdateRequestDto communityPostUpdateRequestDto) {
        CommunityPost communityPost = communityPostRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 동네생활이 없습니다. id="+id));

        communityPost.update(communityPostUpdateRequestDto.getContent());
        return id;
    }

    public CommunityPostResponseDto findById(Long id){
        CommunityPost entity = communityPostRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 동네생활이 없습니다. id="+id));
        return new CommunityPostResponseDto(entity);
    }

}
