package daangnmarket.daangn.project.controller;

import daangnmarket.daangn.project.domain.Member;
import daangnmarket.daangn.project.dto.MemberLoginDto;
import daangnmarket.daangn.project.dto.MemberSaveDto;
import daangnmarket.daangn.project.message.ApiResponse;
import daangnmarket.daangn.project.message.Message;
import daangnmarket.daangn.project.service.MemberService;
import daangnmarket.daangn.project.vo.SignVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {
    private final MemberService memberService;

    // 임시 메서드
    @PostMapping("/login")
    public String login(@RequestBody MemberLoginDto memberLoginDto ){
        return "Login!";
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Long> updatePassword(@PathVariable String id){
        return null;
    }

//////////////////////////////////회원가입/////////////////////
    @PostMapping("/signup")
    public ResponseEntity<Message> signUp(@RequestBody MemberSaveDto memberSaveDto) {
        return memberService.join(memberSaveDto);
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<ApiResponse> saveUser(@RequestBody SignVo signVo){
        Member member = Member.builder()
                .email(signVo.getEmail())
                .password(signVo.getPassword())
                .username(signVo.getUsername())
                .build();
        memberService.save(member);
        return new ResponseEntity<>(new ApiResponse(), HttpStatus.OK);
    }

//    //이메일 중복체크
//    @GetMapping("/exist_user/{email}")
//    public boolean findUserByEmail(@PathVariable String email){
//        Optional<Member> member = memberService.findUserByEmail(email);
//        return member.isPresent() ? true : false;
//    }
}
