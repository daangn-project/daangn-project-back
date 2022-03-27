package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.dto.ProductDTO;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@ControllerAdvice
public class ProductController {
    private final ProductService productService;

    // 전체 게시물 조회
    @GetMapping("")
    public ResponseEntity<Message> showAllProducts() {
        // 전체 게시물 조회
        List<ProductDTO.DetailResponseDTO> productDetailResponseDtoList = productService.findAll();
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("전체 게시물 조회 결과입니다.")
                .data(productDetailResponseDtoList)
                .build(), HttpStatus.OK);
    }

    // 개별 게시물 조회
    // TODO: 1) ResponseEntity의 형태를 변경해야 하는가? 2) DTO를 분리해서 위와 같이 Setter 주입으로 하는 것이 맞는가?
    @GetMapping("/{id}")
    public ResponseEntity<Message> findItemPost(@PathVariable String id) {
        // 게시물의 상세 정보
        ProductDTO.DetailResponseDTO productDetailResponseDto = productService.findById(Long.parseLong(id));

        // 유저가 작성한 게시물도 같이 보여준다.
        List<ProductDTO.ResponseWithMemberDTO> productResponseWithMemberDtoList = productService.findByUserId(productDetailResponseDto.getMemberId());
        productDetailResponseDto.setProductWithMemberDtoList(productResponseWithMemberDtoList);

        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("ID: " + id + " 게시물 조회")
                .data(productDetailResponseDto)
                .build(), HttpStatus.OK);
    }

    // 카테고리에 해당하는 모든 ItemPost 조회
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
