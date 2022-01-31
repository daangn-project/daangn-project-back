package daangnmarket.daangn.project.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -868765630229614668L;
    private final String jwttoken;
}