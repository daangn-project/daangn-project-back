package daangnmarket.daangn.project.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtTokenService implements Serializable {

    private static final long serialVersionUID = -798416586417070603L;
    private static final long JWT_TOKEN_VALIDITY = 7 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    //jwt token 에서 username 검색 기능
    public String getUserNameFromToken(String token){
        try{
            return getClaimFromToken(token, Claims::getSubject);
        }catch (Exception ex){
            throw new UsernameFromTokenException("username from token exception");
        }
    }

    // jwt token 에서 날짜 만료되었는지 검색
    public Date getExpirationDataFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    /**
     * secret 키를 가지고 토큰에서 정보 검색
     * @param token
     * @return
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * 토큰 만료 체크
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * username으로 토큰생성
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

}
