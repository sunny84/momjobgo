package kr.momjobgo.eyou.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.momjobgo.eyou.utils.Converter;
import kr.momjobgo.eyou.web.jpa.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtTokenProvider {

    private static final String JWT_SECRET = "secretKey";

    // 토큰 유효시간
    private static final int JWT_EXPIRATION_MS = 1000    /*ms*/
                                                * 60 /*seconds*/
                                                * 60 /*minutes*/
                                                * 24 /*Hour*/;

    public static String generateToken(UserEntity user) throws JsonProcessingException {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject((String) new Converter<UserEntity>().toJsonString(user) ) // 사용자
                .setIssuedAt(new Date()) // 현재 시간 기반으로 생성
                .setExpiration(expiryDate) // 만료 시간 세팅
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
                .compact();
    }

    // Jwt 토큰에서 아이디 추출
    public static String getUserIdFromJWT(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // Jwt 토큰 유효성 검사
    public static boolean validateToken(String token) {
        Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
        return true;
    }
}
