package daangnmarket.daangn.project.service;

import daangnmarket.daangn.project.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class JwtTokenService implements Serializable {

    private static final long serialVersionUID = -798416586417070603L;
    private static final long JWT_TOKEN_VALIDITY = 7 * 60 * 60;

    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    MemberService memberService;






}
