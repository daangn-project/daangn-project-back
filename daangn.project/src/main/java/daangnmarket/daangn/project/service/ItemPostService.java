package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.ItemCategory;
import daangnmarket.daangn.project.domain.ItemPost;
import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.dto.ItemPostResponseDto;
import daangnmarket.daangn.project.dto.ItemPostSaveDto;
import daangnmarket.daangn.project.dto.PhotoResponseDto;
import daangnmarket.daangn.project.handler.FileHandler;
import daangnmarket.daangn.project.repository.ItemPostRepository;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.repository.PhotoRepository;
import daangnmarket.daangn.project.vo.ItemPostFileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemPostService {
    private final ItemPostRepository itemPostRepository;
    private final MemberRepository memberRepository;
    private final PhotoRepository photoRepository;
    private final FileHandler fileHandler;

    // 생성
    public void save(ItemPostSaveDto itemPostSaveDto, List<MultipartFile> files) throws Exception {
        Member member = memberRepository.findByNickname(itemPostSaveDto.getWriter());
        ItemPost itemPost = ItemPost.builder()
                .member(member)
                .title(itemPostSaveDto.getTitle())
                .description(itemPostSaveDto.getDescription())
                .price(itemPostSaveDto.getPrice())
                .viewCount(0)
                .build();

        List<Photo> photoList = fileHandler.parseFileInfo(files);

        // 파일이 존재할 때에만 처리
        if (!photoList.isEmpty()) {
            for (Photo photo : photoList)
                // 파일을 DB에 저장
                itemPost.addPhoto(photoRepository.save(photo));
        }
        itemPost.setMember(member);
        itemPostRepository.save(itemPost);
    }

    // 수정
    public ItemPostSaveDto update(Long id, ItemPostFileVO itemPostFileVO) {
        ItemPost itemPost = itemPostRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("해당 게시글이 존재하지 않습니다."));
        return ItemPostSaveDto.builder()
                .writer(itemPost.getMember().getNickname())
                .title(itemPostFileVO.getTitle())
                .description(itemPostFileVO.getDescription())
                .price(itemPostFileVO.getPrice())
                .itemCategory(itemPostFileVO.getItemCategory())
                .build();
    }

    // 아이템 포스트에 포함된 모든 사진들을 반환
    public List<PhotoResponseDto> findAllPhotoById(String id) {
        ItemPost itemPost = itemPostRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new NoSuchElementException("해당 게시물이 존재하지 않습니다.")
        );
        return itemPost.getPhotoList().stream().map(PhotoResponseDto::new).collect(Collectors.toList());
    }

//    // 아이템 포스트 조회
//    @Transactional(readOnly = true)
//    public ItemPostResponseDto searchById(String id, List<Long> photoId) {
//        ItemPost itemPost = itemPostRepository.findById(Long.parseLong(id)).orElseThrow(()
//                -> new NoSuchElementException("해당 게시글이 존재하지 않습니다."));
//        return injectMemberIntoItemPostDto(new ItemPostResponseDto(itemPost),Long.parseLong(id));
//    }

    // 삭제
    public void delete(Long id) throws IllegalArgumentException {
        ItemPost itemPost = itemPostRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("해당 게시글이 존재하지 않습니다."));
        itemPostRepository.delete(itemPost);
    }

    // 카테고리로 조회
    @Transactional(readOnly = true)
    public List<ItemPostResponseDto> findByCategory(String category) {
        ItemCategory categoryByEnum = ItemCategory.valueOf(category);
        List<ItemPost> byCategory = itemPostRepository.findByCategory(categoryByEnum);
        return byCategory.stream().map(ItemPostResponseDto::new).collect(Collectors.toList());
    }

    // 모든 게시물 조회
    @Transactional(readOnly = true)
    public List<ItemPostResponseDto> findAll(){
        return itemPostRepository.findAll().stream().map(ItemPostResponseDto::new).collect(Collectors.toList());
    }

    // Id로 게시물 조회
    public ItemPostResponseDto findById(Long id) {
        ItemPost itemPost = itemPostRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("해당 게시글이 존재하지 않습니다."));
        return new ItemPostResponseDto(itemPost);
    }
}
