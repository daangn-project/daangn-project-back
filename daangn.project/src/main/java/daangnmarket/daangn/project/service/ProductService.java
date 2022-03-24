package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.product.ProductCategory;
import daangnmarket.daangn.project.domain.product.Product;
import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.domain.Photo;
import daangnmarket.daangn.project.dto.ProductDTO;
import daangnmarket.daangn.project.handler.S3Uploader;
import daangnmarket.daangn.project.repository.ProductRepository;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final PhotoRepository photoRepository;
    private final S3Uploader s3Uploader;

    // 생성
    public void save(ProductDTO.SaveDto productSaveDto) throws IOException {
        Member member = memberRepository.findByNickname(productSaveDto.getWriter()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        Product product = Product.builder()
                .member(member)
                .title(productSaveDto.getTitle())
                .description(productSaveDto.getDescription())
                .price(productSaveDto.getPrice())
                .productCategory(productSaveDto.getProductCategory())
                .build();

        productSaveDto.getImages().forEach((f) -> {
            try {
                String S3Url = s3Uploader.upload(f, "static");
                product.addPhoto(photoRepository.save(Photo.builder().path(S3Url).build()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        product.setMember(member);
        productRepository.save(product);
    }

    // 수정
//    public ProductSaveDto update(Long id, ItemPostFileVO itemPostFileVO) {
//        Product itemPost = itemPostRepository.findById(id).orElseThrow(()
//                -> new NoSuchElementException("해당 게시글이 존재하지 않습니다."));
//        return ProductSaveDto.builder()
//                .writer(itemPost.getMember().getNickname())
//                .title(itemPostFileVO.getTitle())
//                .description(itemPostFileVO.getDescription())
//                .price(itemPostFileVO.getPrice())
//                .productCategory(itemPostFileVO.getItemCategory())
//                .build();
//    }

    // 삭제
    public void delete(Long id) throws IllegalArgumentException {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("해당 게시글이 존재하지 않습니다."));
        productRepository.delete(product);
    }

    // 카테고리로 조회
    @Transactional(readOnly = true)
    public List<ProductDTO.DetailResponseDTO> findByCategory(String category) {
        ProductCategory categoryByEnum = ProductCategory.valueOf(category);
        List<Product> byCategory = productRepository.findByCategory(categoryByEnum);
        return byCategory.stream().map(ProductDTO.DetailResponseDTO::new).collect(Collectors.toList());
    }

    // 모든 게시물 조회
    @Transactional(readOnly = true)
    public List<ProductDTO.DetailResponseDTO> findAll(){
        return productRepository.findAll().stream().map(ProductDTO.DetailResponseDTO::new).collect(Collectors.toList());
    }

    // Id로 게시물 조회
    public ProductDTO.DetailResponseDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("해당 게시글이 존재하지 않습니다."));
        return new ProductDTO.DetailResponseDTO(product);
    }

    // 유저 ID로 유저가 작성한 게시물 조회
    public List<ProductDTO.ResponseWithMemberDTO> findByUserId(Long id) {
        return productRepository.findByMemberId(id).stream().map(ProductDTO.ResponseWithMemberDTO::new).collect(Collectors.toList());
    }
}
