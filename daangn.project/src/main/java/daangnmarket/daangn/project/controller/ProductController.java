package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.dto.product.ProductByUserDto;
import daangnmarket.daangn.project.dto.product.ProductDetailResponseDto;
import daangnmarket.daangn.project.dto.product.ProductSaveDto;
import daangnmarket.daangn.project.handler.S3Uploader;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.service.ProductService;
import daangnmarket.daangn.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final MemberService memberService;
    private final S3Uploader s3Uploader;

    // 전체 게시물 조회
    @GetMapping("")
    public ResponseEntity<Message> showAllProducts() {
        // 전체 게시물 조회
        List<ProductDetailResponseDto> itemPostResponseDtoList = productService.findAll();
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("전체 게시물 조회 결과입니다.")
                .data(itemPostResponseDtoList)
                .build(), HttpStatus.OK);
    }

    // 개별 게시물 조회
    // TODO: 1) ResponseEntity의 형태를 변경해야 하는가? 2) DTO를 분리해서 위와 같이 Setter 주입으로 하는 것이 맞는가?
    @GetMapping("/{id}")
    public ResponseEntity<Message> findItemPost(@PathVariable String id) {
        // 게시물의 상세 정보
        ProductDetailResponseDto itemPostDetailResponseDto = productService.findById(Long.parseLong(id));

        // 유저가 작성한 게시물도 같이 보여준다.
        List<ProductByUserDto> productByUserDtoList = productService.findByUserId(itemPostDetailResponseDto.getMemberId());
        itemPostDetailResponseDto.setProductByUserDtos(productByUserDtoList);

        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("ID: " + id + " 게시물 조회")
                .data(itemPostDetailResponseDto)
                .build(), HttpStatus.OK);
    }

    // 카테고리에 해당하는 모든 ItemPost 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<Message> showProductsByCategory(@PathVariable String category) {
        List<ProductDetailResponseDto> productDetailResponseDtoList = productService.findByCategory(category);
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message(category + "카테고리에 대한 조회 결과입니다.")
                .data(productDetailResponseDtoList)
                .build(), HttpStatus.OK);
    }


    // 생성
    @PostMapping("")
    public ResponseEntity<Message> createProduct(@ModelAttribute ProductSaveDto productSaveDto) throws IOException{
        productService.save(productSaveDto);
        return new ResponseEntity<>(Message.builder().status(StatusEnum.OK).message("게시물이 등록되었어요.").data(productSaveDto).build(), HttpStatus.OK);
    }

    // 수정
//    @PutMapping("/{id}")
//    public ResponseEntity<Message> updateProduct(@PathVariable Long id, @ModelAttribute ItemPostFileVO itemPostFileVO) {
//        ProductSaveDto updatedDto = productService.update(id, itemPostFileVO);
//        return new ResponseEntity<>(Message.builder()
//                .status(StatusEnum.OK)
//                .message("게시물을 수정했어요.")
//                .data(updatedDto)
//                .build(), HttpStatus.OK);
//    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable Long id) {
        try{
            productService.delete(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
