package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.dto.ItemPostSaveDto;
import daangnmarket.daangn.project.dto.communitypost.CommunityPostResponseDto;
import daangnmarket.daangn.project.dto.communitypost.CommunityPostSaveDto;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/community-posts")
public class CommunityPostController {
    private final CommunityPostService communityPostService;
    private final MemberService memberService;

    @GetMapping("")
    public ResponseEntity<Message> showAllCommunityPosts() {
        // 전체 동네 생활 조회
        List<CommunityPostResponseDto> communityPostResponseDtoList = communityPostService.findAll();
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("전체 동네 생활 조회 결과입니다.")
                .data(communityPostResponseDtoList)
                .build(), HttpStatus.OK);
    }
    // 개별 동네생활 조회 by id
    @GetMapping("/{id}")
    public ResponseEntity<Message> showCommunityPost(@PathVariable String id) {
        CommunityPostResponseDto communityPostResponseDto = communityPostService.findById(Long.parseLong(id));
        return new ResponseEntity<>(Message.builder()
                .status(StatusEnum.OK)
                .message("ID: " + id + " 게시물 조회")
                .data(communityPostResponseDto)
                .build(), HttpStatus.OK);
    }

    // 카테고리에 해당하는 모든 CommunityPost 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<Message> showCommunityPostByCategory(@PathVariable String category) {
        List<CommunityPostResponseDto> communityPostResponseDtoList = communityPostService.findByCategory(category);
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

        CommunityPostSaveDto communityPostSaveDto = CommunityPostSaveDto.builder()
                .writer(member.getNickname())
                .title(communityPostFileVO.getTitle())
                .description(communityPostFileVO.getDescription())
                .communityCategory(communityPostFileVO.getCommunityCategory())
                .build();

        communityPostService.save(communityPostSaveDto, communityPostFileVO.getFiles());
        return new ResponseEntity<>(Message.builder().status(StatusEnum.OK).message("동네 생활이 생성되었어요.").data(communityPostSaveDto).build(), HttpStatus.OK);
    }
//    @PutMapping("/{id}")
//    public Long updateCommunityPost(@PathVariable Long id, @RequestBody CommunityPostUpdateRequestDto communityPostUpdateRequestDto) {
//        return communityPostService.update(id,communityPostUpdateRequestDto);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Long> deleteCommunityPost(@PathVariable String id) {
//        return null;
//    }
}
