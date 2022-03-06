package daangnmarket.daangn.project.dto.member;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class MemberLoginDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @Size(min = 3, max = 100)
    private String password;
}