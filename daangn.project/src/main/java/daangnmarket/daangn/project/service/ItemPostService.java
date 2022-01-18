package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.ItemPost;
import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.dto.ItemPostResponseDto;
import daangnmarket.daangn.project.dto.ItemPostSaveDto;
import daangnmarket.daangn.project.dto.PhotoResponseDto;
import daangnmarket.daangn.project.handler.FileHandler;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.repository.ItemPostRepository;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.repository.PhotoRepository;
import daangnmarket.daangn.project.vo.ItemPostFileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemPostService {
    private final ItemPostRepository itemPostRepository;
    private final MemberRepository memberRepository;
    private final PhotoRepository photoRepository;
    private final FileHandler fileHandler;

    // 생성
    public Long save(ItemPostSaveDto itemPostSaveDto, List<MultipartFile> files) throws Exception{
        Member member = memberRepository.findByNickname(itemPostSaveDto.getWriter());
        ItemPost itemPost = ItemPost.builder()
                .member(member)
                .title(itemPostSaveDto.getTitle())
                .description(itemPostSaveDto.getDescription())
                .price(itemPostSaveDto.getPrice())
                .build();

        List<Photo> photoList = fileHandler.parseFileInfo(files);

        // 파일이 존재할 때에만 처리
        if(!photoList.isEmpty()){
            for(Photo photo : photoList)
                // 파일을 DB에 저장
                itemPost.addPhoto(photoRepository.save(photo));
        }
        return itemPostRepository.save(itemPost).getId();
    }

    public ItemPostSaveDto update(Long id, ItemPostFileVO itemPostFileVO){
        ItemPost itemPost = itemPostRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return ItemPostSaveDto.builder()
                .writer(itemPost.getMember().getNickname())
                .title(itemPostFileVO.getTitle())
                .description(itemPostFileVO.getDescription())
                .itemCategory(itemPostFileVO.getItemCategory())
                .build();
    }

    // 아이템 포스트에 포함된 모든 사진들을 반환
    public List<PhotoResponseDto> findAllPhotoById(String id) {
        ItemPost itemPost = itemPostRepository.findById(Long.parseLong(id)).get();
        return itemPost.getPhotoList().stream().map(PhotoResponseDto::new).collect(Collectors.toList());
    }

    // 아이템 포스트 조회
    @Transactional(readOnly = true)
    public ItemPostResponseDto searchById(String id, List<Long> photoId) {
        ItemPost itemPost = itemPostRepository.findById(Long.parseLong(id)).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return new ItemPostResponseDto(itemPost, photoId);
    }

    // 삭제
    public void delete(Long id) throws IllegalArgumentException{
        ItemPost itemPost = itemPostRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        itemPostRepository.delete(itemPost);
    }

}
