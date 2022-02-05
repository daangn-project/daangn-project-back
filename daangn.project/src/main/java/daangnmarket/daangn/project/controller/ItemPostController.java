package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.dto.ItemPostResponseDto;
import daangnmarket.daangn.project.dto.ItemPostSaveDto;
import daangnmarket.daangn.project.dto.PhotoResponseDto;
import daangnmarket.daangn.project.handler.S3Uploader;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.service.ItemPostService;
import daangnmarket.daangn.project.service.MemberService;
import daangnmarket.daangn.project.vo.ItemPostFileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item-posts")
public class ItemPostController {
    private final ItemPostService itemPostService;
    private final MemberService memberService;
    private final S3Uploader s3Uploader;

    // 전체 게시물 조회
    @GetMapping("")
    public ResponseEntity<Message> showAllItemPosts() {
        // 전체 게시물 조회
        List<ItemPostResponseDto> itemPostResponseDtoList = itemPostService.findAll();
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("전체 게시물 조회 결과입니다.")
                .data(itemPostResponseDtoList)
                .build(), HttpStatus.OK);
    }

    // 개별 게시물 조회 by id
    @GetMapping("/{id}")
    public ResponseEntity<Message> showItemPost(@PathVariable String id) {
        ItemPostResponseDto itemPostResponseDto = itemPostService.findById(Long.parseLong(id));
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("ID: " + id + " 게시물 조회")
                .data(itemPostResponseDto)
                .build(), HttpStatus.OK);
    }

    // 회원이 등록한 itemPost 조회


    // 카테고리에 해당하는 모든 ItemPost 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<Message> showItemPostByCategory(@PathVariable String category) {
        List<ItemPostResponseDto> itemPostResponseDtoList = itemPostService.findByCategory(category);
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message(category + "카테고리에 대한 조회 결과입니다.")
                .data(itemPostResponseDtoList)
                .build(), HttpStatus.OK);
    }


    // 생성
    @PostMapping("")
    public ResponseEntity<Message> createItemPost(@ModelAttribute ItemPostFileVO itemPostFileVO) throws IOException{
        Member member = memberService.findById(Long.parseLong(itemPostFileVO.getMemberId()));
        ItemPostSaveDto itemPostSaveDto = ItemPostSaveDto.builder()
                .writer(member.getNickname())
                .title(itemPostFileVO.getTitle())
                .description(itemPostFileVO.getDescription())
                .price(itemPostFileVO.getPrice())
                .itemCategory(itemPostFileVO.getItemCategory())
                .build();

        itemPostService.save(itemPostSaveDto, itemPostFileVO.getFiles());
        return new ResponseEntity<>(Message.builder().status(StatusEnum.OK).message("게시물이 등록되었어요.").data(itemPostSaveDto).build(), HttpStatus.OK);
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateItemPost(@PathVariable Long id, @ModelAttribute ItemPostFileVO itemPostFileVO) {
        ItemPostSaveDto updatedDto = itemPostService.update(id, itemPostFileVO);
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("게시물을 수정했어요.")
                .data(updatedDto)
                .build(), HttpStatus.OK);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteItemPost(@PathVariable Long id) {
        try {
            itemPostService.delete(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
