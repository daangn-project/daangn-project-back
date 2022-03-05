package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.dto.community.CommunityCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community-comment")
public class CommunityCommentController {

    // 동네생활 댓글 생성
    @PostMapping("")
    public ResponseEntity<Long> save(@RequestBody CommunityCommentDto commentDto){
//        Long createdId = commentService.save(commentDt);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
