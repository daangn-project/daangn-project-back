package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.domain.CommunityPost;
import daangnmarket.daangn.project.dto.communitypost.CommunityPostResponseDto;
import daangnmarket.daangn.project.dto.communitypost.CommunityPostSaveRequestDto;
import daangnmarket.daangn.project.dto.communitypost.CommunityPostUpdateRequestDto;
import daangnmarket.daangn.project.service.CommunityPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community-posts")
public class CommunityPostController {
    private final CommunityPostService communityPostService;

    @GetMapping("/{id}")
    public CommunityPostResponseDto showCommunityPost(@PathVariable Long id, Model model) {
        CommunityPostResponseDto communityPost = communityPostService.findById(id);
//        model.addAttribute(con,communityPost.getContent())
        return communityPost;
    }

    // ResponseEntity<Long>
    @PostMapping
    public Long createCommunityPost(@RequestBody CommunityPostSaveRequestDto communityPostSaveRequestDto){
        return communityPostService.save(communityPostSaveRequestDto);
    }

    @PutMapping("/{id}")
    public Long updateCommunityPost(@PathVariable Long id, @RequestBody CommunityPostUpdateRequestDto communityPostUpdateRequestDto) {
        return communityPostService.update(id,communityPostUpdateRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCommunityPost(@PathVariable String id) {
        return null;
    }
}
