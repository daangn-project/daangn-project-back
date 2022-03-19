package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.domain.Member;

import daangnmarket.daangn.project.dto.comment.CommentResponseDto;
import daangnmarket.daangn.project.dto.community.CommunityResponseDto;
import daangnmarket.daangn.project.dto.community.CommunitySaveDto;

import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.service.CommunityService;
import daangnmarket.daangn.project.service.MemberService;
import daangnmarket.daangn.project.vo.CommunityPostFileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communities")
public class CommunityController {
    private final CommunityService communityService;

    @PostMapping("")
    public ResponseEntity<Message> communityCreate(@ModelAttribute CommunitySaveDto communitySaveDto) throws IOException {
        communityService.save(communitySaveDto);
        return new ResponseEntity<>(Message.builder().status(StatusEnum.OK).message("동네 생활이 생성되었어요.").data(communitySaveDto).build(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Message> communityList() {
        List<CommunityResponseDto> communityPostResponseDtoList = communityService.findAll();
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("전체 동네 생활 조회 결과입니다.")
                .data(communityPostResponseDtoList)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> communityDetails(@PathVariable String id) {
        CommunityResponseDto communityResponseDto = communityService.findById(Long.parseLong(id));
        communityResponseDto.sortCommentsByParentOrderThenCommentOrder();
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("ID: " + id + " 게시물 조회")
                .data(communityResponseDto)
                .build(), HttpStatus.OK);
    }

    // 카테고리에 해당하는 모든 CommunityPost 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<Message> communityListByCategory(@PathVariable String category) {
        List<CommunityResponseDto> communityPostResponseDtoList = communityService.findByCategory(category);
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message(category + "카테고리에 대한 동네 생활 조회 결과입니다.")
                .data(communityPostResponseDtoList)
                .build(), HttpStatus.OK);
    }


//    // 동네 생활 수정
//    @PutMapping("/{id}")
//    public ResponseEntity<Message> communityModify(@PathVariable Long id, @ModelAttribute CommunityPostFileVO communityPostFileVO) {
//        CommunitySaveDto communitySaveDto = communityService.update(id, communityPostFileVO);
//        return new ResponseEntity<>(Message.builder()
//                .status(StatusEnum.OK)
//                .message("동네 생활을 수정했어요.")
//                .data(communitySaveDto)
//                .build(), HttpStatus.OK);
//    }

    // 동네 생활삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> communityRemove(@PathVariable Long id) {
        try {
            communityService.delete(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
