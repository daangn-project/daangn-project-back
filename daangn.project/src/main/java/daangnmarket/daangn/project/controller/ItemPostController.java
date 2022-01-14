package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.dto.ItemPostSaveDto;
import daangnmarket.daangn.project.dto.ItemPostUpdateDto;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.service.ItemPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item-posts")
public class ItemPostController {
    private final ItemPostService itemPostService;

    @GetMapping("/{id}")
    public ResponseEntity<Long> showItemPost(@PathVariable String id) {
        return null;
    }


    @PostMapping("")
    public ResponseEntity<Message> createItemPost(@RequestBody ItemPostSaveDto itemPostSaveDto){
        return itemPostService.save(itemPostSaveDto);

    }

    // updateItemPost 컨트롤러에서 수정하려는 itemPost id를 pathvariable로 받고 수정할 값들을 dto로 뺄지, 아니면 id까지 Dto에 담을지?
    @PutMapping
    public ResponseEntity<Long> updateItemPost(@RequestBody ItemPostUpdateDto itemPostUpdateDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteItemPost(@PathVariable String id) {
        return null;
    }

}
