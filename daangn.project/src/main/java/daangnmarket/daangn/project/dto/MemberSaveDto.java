package daangnmarket.daangn.project.dto;

import daangnmarket.daangn.project.domain.Member;
import lombok.Data;

@Data
public class MemberSaveDto {

    private String username;
    private String password;
    private String nickname;
    private String email;

    public Member toEntity(){
        return Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .build();
    }
}
