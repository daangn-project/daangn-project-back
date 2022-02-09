package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.ItemCategory;
import daangnmarket.daangn.project.domain.ItemPost;
import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.dto.ItemPostResponseDto;
import daangnmarket.daangn.project.dto.ItemPostSaveDto;
import daangnmarket.daangn.project.dto.PhotoResponseDto;
import daangnmarket.daangn.project.handler.S3Uploader;
import daangnmarket.daangn.project.repository.ItemPostRepository;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.repository.PhotoRepository;
import daangnmarket.daangn.project.vo.ItemPostFileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemPostService {
    private final ItemPostRepository itemPostRepository;
    private final MemberRepository memberRepository;
    private final PhotoRepository photoRepository;
    private final S3Uploader s3Uploader;

    // 생성
    public void save(ItemPostSaveDto itemPostSaveDto, List<MultipartFile> files) throws IOException {
        Member member = memberRepository.findByNickname(itemPostSaveDto.getWriter());
        ItemPost itemPost = ItemPost.builder()
                .member(member)
                .title(itemPostSaveDto.getTitle())
                .description(itemPostSaveDto.getDescription())
                .price(itemPostSaveDto.getPrice())
                .itemCategory(itemPostSaveDto.getItemCategory())
                .viewCount(0)
                .build();

        files.forEach((f) -> {
            try {
                String S3Url = s3Uploader.upload(f, "static");
                itemPost.addPhoto(photoRepository.save(Photo.builder().path(S3Url).build()));
                itemPostSaveDto.getPhotoList().add(S3Url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
