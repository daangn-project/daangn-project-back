package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.dto.CommentDTO;
import daangnmarket.daangn.project.dto.ProductDTO;
import daangnmarket.daangn.project.dto.ProductLikeDTO;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.repository.ProductLikeRepository;
import daangnmarket.daangn.project.service.ProductLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class ProductLikeController {

    private final ProductLikeService productLikeService;
    private final ProductLikeRepository productLikeRepository;

    public boolean isAlreadyLike(Long user_id, Long post_id) {
        return productLikeRepository.findByUserIdAndPostId(user_id, post_id).isPresent();
    }

    // 좋아요
    @PostMapping("")
    public ResponseEntity<Message> createLike(@ModelAttribute ProductLikeDTO.LikeRequestDto likeRequestDto) throws IOException {
        productLikeService.save(likeRequestDto);
        return new ResponseEntity<>(
                Message.builder()
                        .status(StatusEnum.OK)
                        .message("좋아요생성!")
                        .data(likeRequestDto)
                        .build(), HttpStatus.OK
        );
    }

    // 좋아요 삭제
//    @DeleteMapping()
//    public ResponseEntity<Message> deleteLike
}
