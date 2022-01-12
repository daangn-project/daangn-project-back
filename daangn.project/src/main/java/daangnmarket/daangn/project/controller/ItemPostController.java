package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.dto.MemberSaveDto;
import daangnmarket.daangn.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item-posts")
public class ItemPostController {
    private final ItemPostService itemPostService;

    @GetMapping("/{id}")
    public ResponseEntity<Long> showPostItem(@PathVariable String id) {
        return null;
    }


    @PostMapping
    public ResponseEntity<Long> createPostItem(@RequestBody ItemPostSaveDto itemPostSaveDto){
        return null;
    }

    // updateItemPost 컨트롤러에서 수정하려는 itemPost id를 pathvariable로 받고 수정할 값들을 dto로 뺄지, 아니면 id까지 Dto에 담을지?
    @PutMapping
    public ResponseEntity<Long> updatePostItem(@RequestBody ItemPostUpdateDto itemPostUpdateDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deletePostItem(@PathVariable String id) {
        return null;
    }

}
