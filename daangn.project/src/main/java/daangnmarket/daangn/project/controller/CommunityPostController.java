package daangnmarket.daangn.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community-posts")
public class CommunityPostController {
    private final CommunityPostService communityPostService;

    @GetMapping("/{id}")
    public ResponseEntity<Long> showCommunityPost(@PathVariable String id) {
        return null;
    }


    @PostMapping
    public ResponseEntity<Long> createCommunityPost(@RequestBody CommunityPostSaveDto communityPostSaveDto){
        return null;
    }


    @PutMapping
    public ResponseEntity<Long> updateCommunityPost(@RequestBody CommunityPostUpdateDto communityPostUpdateDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCommunityPost(@PathVariable String id) {
        return null;
    }
}
