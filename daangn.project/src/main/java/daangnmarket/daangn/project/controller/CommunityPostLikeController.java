package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.repository.MemberRepository;
import daangnmarket.daangn.project.service.CommunityPostLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommunityPostLikeController {
    private final CommunityPostLikeService communityPostLikeService;
    private final MemberRepository memberRepository;

    @PostMapping("/like/{id}")
    public ResponseEntity<String> addLike(
            @RequestParam("username") String username,
            @PathVariable Long id) {

        Member member = memberRepository.findByUsername(username).orElseThrow();
        boolean result = false;
        result = communityPostLikeService.addLike(member, id);

        return result ?
            new ResponseEntity<>(HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
