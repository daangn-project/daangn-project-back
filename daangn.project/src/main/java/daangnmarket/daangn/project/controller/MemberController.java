package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.domain.member.Member;
import daangnmarket.daangn.project.dto.MemberDTO;
import daangnmarket.daangn.project.message.ApiResponse;
import daangnmarket.daangn.project.service.CustomUserDetailsImpl;
import daangnmarket.daangn.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    @ResponseBody
    @Transactional
    public ResponseEntity<ApiResponse> saveUser(@RequestBody MemberDTO.SignUpDto signUpDto){
        Long signedMemberId = memberService.signUp(signUpDto);
        return new ResponseEntity<>(new ApiResponse(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody MemberDTO.LoginDTO memberLoginDto)
    {
        return memberService.login(memberLoginDto);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Long> updatePassword(@PathVariable String id){
        return null;
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<MemberDTO.infoDTO> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(memberService.getMyUserWithAuthorities());
    }

    @GetMapping("/{username}")
    public ResponseEntity<MemberDTO.infoDTO> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(memberService.getUserWithAuthorities(username));
    }
}
