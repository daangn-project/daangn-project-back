package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.dto.CommentDTO;
import daangnmarket.daangn.project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    // 등록
    @PostMapping("")
    public ResponseEntity<CommentDTO.ResponseDTO> CommentCreate(@RequestBody CommentDTO.SaveDTO commentSaveDto){
        return ResponseEntity.ok(commentService.save(commentSaveDto));
    }

    // DB에서 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> CommentRemove(@PathVariable Long id){
        commentService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    // 내용을 바꿈으로써 삭제
    @PutMapping("/{id}")
    public ResponseEntity<Long> CommentRemoveByMessage(@PathVariable Long id){
        commentService.deleteByMessage(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
