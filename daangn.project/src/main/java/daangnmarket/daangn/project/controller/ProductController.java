package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.dto.ProductDTO;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@ControllerAdvice
public class ProductController {
    private final ProductService productService;
    private static final int PAGE_DEFAULT_SIZE = 10;

    @GetMapping("")
    public ResponseEntity<Message> productList(Long cursor) {
        List<ProductDTO.DetailResponseDTO> productDetailResponseDtoList = productService.findProductsByPage(cursor, PageRequest.of(0, PAGE_DEFAULT_SIZE));
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("게시물 조회 결과입니다.")
                .data(productDetailResponseDtoList)
                .build(), HttpStatus.OK);
    }

    // 개별 게시물 조회
    @GetMapping("/{id}")
    public ResponseEntity<Message> findItemPost(@PathVariable String id) {
        // 게시물의 상세 정보
        ProductDTO.DetailResponseDTO productDetailResponseDto = productService.findById(Long.parseLong(id));

        // 유저가 작성한 게시물도 같이 보여준다.
        List<ProductDTO.ResponseWithMemberDTO> productResponseWithMemberDtoList = productService.findByMemberId(productDetailResponseDto.getMemberId());
        productDetailResponseDto.setProductWithMemberDtoList(productResponseWithMemberDtoList);

        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("ID: " + id + " 게시물 조회")
                .data(productDetailResponseDto)
                .build(), HttpStatus.OK);
    }

    // 카테고리에 해당하는 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<Message> showProductsByCategory(@PathVariable String category) {
        List<ProductDTO.DetailResponseDTO> productDetailResponseDtoList = productService.findByCategory(category);
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message(category + "카테고리에 대한 조회 결과입니다.")
                .data(productDetailResponseDtoList)
                .build(), HttpStatus.OK);
    }

    // 생성
    @PostMapping("")
    public ProductDTO.DetailResponseDTO createProduct(@ModelAttribute @Valid ProductDTO.SaveDto productSaveDto) {
        return new ProductDTO.DetailResponseDTO(productService.save(productSaveDto));
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
