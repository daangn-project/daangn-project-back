package daangnmarket.daangn.project.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import daangnmarket.daangn.project.domain.Member;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;

//    private Set<AuthorityDto> authorityDtoSet;

    @NotNull
    @Email
    private String email;

//    public static MemberDto from(Member member) {
//        if(member == null) return null;
//
//        return MemberDto.builder()
//                .username(member.getUsername())
//                .nickname(member.getNickname())
//                .email(member.getEmail())
//                .authorityDtoSet(member.getAuthorities().stream()
//                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
//                        .collect(Collectors.toSet()))
//                .build();
//    }

//    public Member toEntity(){
//        return Member.builder()
//                .username(username)
//                .password(password)
//                .nickname(nickname)
//                .email(email)
//                .build();
//    }
}
