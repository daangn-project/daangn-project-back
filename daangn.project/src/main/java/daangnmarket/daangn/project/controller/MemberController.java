package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.dto.MemberDTO;
import daangnmarket.daangn.project.message.ApiResponse;
import daangnmarket.daangn.project.service.MemberService;
import daangnmarket.daangn.project.vo.SignVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {
    private final MemberService memberService;

    // 임시 메서드
    @PostMapping("/login")
    public String login(@RequestBody MemberDTO.LoginDTO memberLoginDto ){
        return "Login!";
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Long> updatePassword(@PathVariable String id){
        return null;
    }

    // 회원가입
    @PostMapping("/signup")
    @ResponseBody
    @Transactional
    public ResponseEntity<ApiResponse> saveUser(@RequestBody SignVo signVo){

        memberService.signup(signVo);
        return new ResponseEntity<>(new ApiResponse(), HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<MemberDTO.infoDTO> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(memberService.getMyUserWithAuthorities());
    }

    @GetMapping("/{username}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MemberDTO.infoDTO> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(memberService.getUserWithAuthorities(username));
    }

//    //이메일 중복체크
//    @GetMapping("/exist_user/{userName}")
//    public boolean findUserByName(@PathVariable String userName){
//        Optional<Member> member = memberService.findByUsername(userName);
//        return member.isPresent() ? true : false;
//    }
}
