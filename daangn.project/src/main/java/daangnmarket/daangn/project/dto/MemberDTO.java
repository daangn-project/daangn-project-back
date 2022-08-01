package daangnmarket.daangn.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import daangnmarket.daangn.project.domain.member.Member;
import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MemberDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthorityDTO {
        private String authorityName;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class infoDTO {

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

        private Set<AuthorityDTO> authorityDtoSet;

        @NotNull
        @Email
        private String email;

        public static MemberDTO.infoDTO from(Member member) {
            if(member == null) return null;
            return MemberDTO.infoDTO.builder()
                    .username(member.getUsername())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .authorityDtoSet(member.getAuthorities().stream()
                            .map(authority -> AuthorityDTO.builder().authorityName(authority.getAuthorityName()).build())
                            .collect(Collectors.toSet()))
                    .build();
        }
    }
    @Data
    public static class SignUpDto {
        private String username;

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
        private String nickname;
        private String password;

        @NotBlank(message = "이메일 주소를 입력해주세요.")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        private String email;
        private String appendDate;
        private String updateDate;

        private String userAuth;
    }


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class LoginDTO {

        @NotNull
        @Size(min = 3, max = 50)
        private String username;

        @NotNull
        @Size(min = 3, max = 100)
        private String password;
    }


    @Data
    public static class ResponseDto {
        private String nickname;
        private String email;
        private List<ProductDTO.DetailResponseDTO> productDetailResponseDtoList;

        public ResponseDto(Member member){
            this.nickname = member.getNickname();
            this.email = member.getNickname();
            this.productDetailResponseDtoList = member.getProductList()
                    .stream().map(ProductDTO.DetailResponseDTO::new).collect(Collectors.toList());
        }
    }

    @Data
    public static class ChatProfileDto{
        private String nickname;
        private String profileImage;

        public ChatProfileDto(Member member){
            this.nickname = member.getNickname();
            this.profileImage = member.getProfileImg();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenDTO {
        private String token;
    }
}
