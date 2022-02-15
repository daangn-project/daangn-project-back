package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.dto.communitypost.CommunityPostResponseDto;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.message.StatusEnum;
import daangnmarket.daangn.project.service.CommunityPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community-posts")
public class CommunityPostController {
    private final CommunityPostService communityPostService;

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


//    // ResponseEntity<Long>
//    @PostMapping
//    public Long createCommunityPost(@RequestBody CommunityPostSaveRequestDto communityPostSaveRequestDto){
//        return communityPostService.save(communityPostSaveRequestDto);
//    }
//
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
