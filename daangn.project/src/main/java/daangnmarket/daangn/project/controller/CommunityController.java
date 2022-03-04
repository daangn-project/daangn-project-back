package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.domain.Member;

import daangnmarket.daangn.project.dto.community.CommunityResponseDto;
import daangnmarket.daangn.project.dto.community.CommunitySaveDto;

import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.service.CommunityPostService;
import daangnmarket.daangn.project.service.MemberService;
import daangnmarket.daangn.project.vo.CommunityPostFileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communities")
public class CommunityController {
    private final CommunityPostService communityPostService;
    private final MemberService memberService;

    @GetMapping("")
    public ResponseEntity<Message> showAllCommunityPosts() {
        // 전체 동네 생활 조회
        List<CommunityResponseDto> communityPostResponseDtoList = communityPostService.findAll();
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("전체 동네 생활 조회 결과입니다.")
                .data(communityPostResponseDtoList)
                .build(), HttpStatus.OK);
    }
    // 개별 동네생활 조회 by id
    @GetMapping("/{id}")
    public ResponseEntity<Message> showCommunityPost(@PathVariable String id) {
        CommunityResponseDto communityResponseDto = communityPostService.findById(Long.parseLong(id));
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("ID: " + id + " 게시물 조회")
                .data(communityResponseDto)
                .build(), HttpStatus.OK);
    }

    // 카테고리에 해당하는 모든 CommunityPost 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<Message> showCommunityPostByCategory(@PathVariable String category) {
        List<CommunityResponseDto> communityPostResponseDtoList = communityPostService.findByCategory(category);
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message(category + "카테고리에 대한 동네 생활 조회 결과입니다.")
                .data(communityPostResponseDtoList)
                .build(), HttpStatus.OK);
    }

    // 동네 생활 생성
    @PostMapping("")
    public ResponseEntity<Message> createCommunityPost(@ModelAttribute CommunityPostFileVO communityPostFileVO) throws IOException {
        Member member = memberService.findById(Long.parseLong(communityPostFileVO.getMemberId()));

        CommunitySaveDto communityPostSaveDto = CommunitySaveDto.builder()
                .writer(member.getNickname())
                .title(communityPostFileVO.getTitle())
                .description(communityPostFileVO.getDescription())
                .communityCategory(communityPostFileVO.getCommunityCategory())
                .build();

        communityPostService.save(communityPostSaveDto, communityPostFileVO.getFiles());
        return new ResponseEntity<>(Message.builder().status(StatusEnum.OK).message("동네 생활이 생성되었어요.").data(communityPostSaveDto).build(), HttpStatus.OK);
    }
    // 동네 생활 수정
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateCommunityPost(@PathVariable Long id, @ModelAttribute CommunityPostFileVO communityPostFileVO) {
        CommunitySaveDto communitySaveDto = communityPostService.update(id, communityPostFileVO);
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("동네 생활을 수정했어요.")
                .data(communitySaveDto)
                .build(), HttpStatus.OK);
    }

    // 동네 생활삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCommunityPost(@PathVariable Long id) {
        try {
            communityPostService.delete(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
