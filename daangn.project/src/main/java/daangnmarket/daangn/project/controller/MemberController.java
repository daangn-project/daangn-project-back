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
import org.springframework.transaction.annotation.Transactional;
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

    // 회원가입
    @PostMapping("/signup")
    @ResponseBody
    @Transactional
    public ResponseEntity<ApiResponse> saveUser(@RequestBody SignVo signVo){

        memberService.save(signVo);
        return new ResponseEntity<>(new ApiResponse(), HttpStatus.OK);
    }

//    //이메일 중복체크
//    @GetMapping("/exist_user/{userName}")
//    public boolean findUserByName(@PathVariable String userName){
//        Optional<Member> member = memberService.findByUsername(userName);
//        return member.isPresent() ? true : false;
//    }
}
