package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.dto.ItemPostResponseDto;
import daangnmarket.daangn.project.dto.ItemPostSaveDto;
import daangnmarket.daangn.project.dto.ItemPostUpdateDto;
import daangnmarket.daangn.project.dto.PhotoResponseDto;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.service.ItemPostService;
import daangnmarket.daangn.project.service.MemberService;
import daangnmarket.daangn.project.vo.ItemPostFileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item-posts")
public class ItemPostController {
    private final ItemPostService itemPostService;
    private final MemberService memberService;

    @GetMapping("/{id}")
    public ResponseEntity<Message> showItemPost(@PathVariable String id) {
        // 게시글 id로 해당 게시글 첨부파일 전체 조회
        List<PhotoResponseDto> photoResponseDtoList = itemPostService.findAllPhotoById(id);

        // 게시글 첨부파일 id 담을 List 객체 생성
        List<Long> photoId = new ArrayList<>();
        // 각 첨부파일 id 추가
        for(PhotoResponseDto photoResponseDto : photoResponseDtoList)
            photoId.add(photoResponseDto.getFileId());

        // 게시글 id와 첨부파일 id 목록 전달받아 결과 반환
        ItemPostResponseDto itemPostResponseDto = itemPostService.searchById(id, photoId);
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("ID: " + id + " 게시물 조회")
                .data(itemPostResponseDto)
                .build(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Message> createItemPost(@ModelAttribute ItemPostFileVO itemPostFileVO) throws Exception{
        Member member = memberService.findById(Long.parseLong(itemPostFileVO.getMemberId()));

        ItemPostSaveDto itemPostSaveDto = ItemPostSaveDto.builder()
                .writer(member.getNickname())
                .title(itemPostFileVO.getTitle())
                .description(itemPostFileVO.getDescription())
                .itemCategory(itemPostFileVO.getItemCategory())
                .build();

        itemPostService.save(itemPostSaveDto, itemPostFileVO.getFiles());
        return new ResponseEntity<>(Message.builder().status(StatusEnum.OK).message("게시물이 등록되었어요.").build(), HttpStatus.OK);
    }

    // updateItemPost 컨트롤러에서 수정하려는 itemPost id를 pathvariable로 받고 수정할 값들을 dto로 뺄지, 아니면 id까지 Dto에 담을지?
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateItemPost(@PathVariable Long id, @ModelAttribute ItemPostFileVO itemPostFileVO) {
        ItemPostSaveDto updatedDto = itemPostService.update(id, itemPostFileVO);

        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("게시물을 수정했어요.")
                .data(updatedDto)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteItemPost(@PathVariable Long id) {
        try {
            itemPostService.delete(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
