package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.product.ProductCategory;
import daangnmarket.daangn.project.domain.product.Product;
import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.dto.product.ProductByUserDto;
import daangnmarket.daangn.project.dto.product.ProductDetailResponseDto;
import daangnmarket.daangn.project.dto.product.ProductSaveDto;
import daangnmarket.daangn.project.dto.PhotoResponseDto;
import daangnmarket.daangn.project.handler.S3Uploader;
import daangnmarket.daangn.project.repository.ProductRepository;
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
public class ProductService {
    private final ProductRepository itemPostRepository;
    private final MemberRepository memberRepository;
    private final PhotoRepository photoRepository;
    private final S3Uploader s3Uploader;

    // 생성
    public void save(ProductSaveDto productSaveDto, List<MultipartFile> files) throws IOException {
        Member member = memberRepository.findByNickname(productSaveDto.getWriter()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        Product itemPost = Product.builder()
                .member(member)
                .title(productSaveDto.getTitle())
                .description(productSaveDto.getDescription())
                .price(productSaveDto.getPrice())
                .productCategory(productSaveDto.getProductCategory())
                .viewCount(0)
                .build();

        files.forEach((f) -> {
            try {
                String S3Url = s3Uploader.upload(f, "static");
                itemPost.addPhoto(photoRepository.save(Photo.builder().path(S3Url).build()));
                productSaveDto.getPhotoList().add(S3Url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        itemPost.setMember(member);
        itemPostRepository.save(itemPost);
    }

    // 수정
    public ProductSaveDto update(Long id, ItemPostFileVO itemPostFileVO) {
        Product itemPost = itemPostRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("해당 게시글이 존재하지 않습니다."));
        return ProductSaveDto.builder()
                .writer(itemPost.getMember().getNickname())
                .title(itemPostFileVO.getTitle())
                .description(itemPostFileVO.getDescription())
                .price(itemPostFileVO.getPrice())
                .productCategory(itemPostFileVO.getItemCategory())
                .build();
    }

    // 아이템 포스트에 포함된 모든 사진들을 반환
    public List<PhotoResponseDto> findAllPhotoById(String id) {
        Product itemPost = itemPostRepository.findById(Long.parseLong(id)).orElseThrow(
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
        Product itemPost = itemPostRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("해당 게시글이 존재하지 않습니다."));
        itemPostRepository.delete(itemPost);
    }

    // 카테고리로 조회
    @Transactional(readOnly = true)
    public List<ProductDetailResponseDto> findByCategory(String category) {
        ProductCategory categoryByEnum = ProductCategory.valueOf(category);
        List<Product> byCategory = itemPostRepository.findByCategory(categoryByEnum);
        return byCategory.stream().map(ProductDetailResponseDto::new).collect(Collectors.toList());
    }

    // 모든 게시물 조회
    @Transactional(readOnly = true)
    public List<ProductDetailResponseDto> findAll(){
        return itemPostRepository.findAll().stream().map(ProductDetailResponseDto::new).collect(Collectors.toList());
    }

    // Id로 게시물 조회
    public ProductDetailResponseDto findById(Long id) {
        Product itemPost = itemPostRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("해당 게시글이 존재하지 않습니다."));
        return new ProductDetailResponseDto(itemPost);
    }

    // 유저 ID로 유저가 작성한 게시물 조회
    public List<ProductByUserDto> findByUserId(Long id) {
        return itemPostRepository.findByMemberId(id).stream().map(ProductByUserDto::new).collect(Collectors.toList());
    }
}
