package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.dto.MemberSaveDto;
import daangnmarket.daangn.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(@RequestBody MemberSaveDto memberSaveDto){
        return null;
    }
    @PutMapping("/{id}/password")
    public ResponseEntity<Long> updatePassword(@PathVariable String id){
        return null;
    }


}
