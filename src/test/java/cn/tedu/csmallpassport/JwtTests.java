package cn.tedu.csmallpassport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTests {
    String secretKey = "da13*(!ahw2i%^!ksb($*&!4&?>$)kas<@jpsa";

    @Test
    public void testGenerate() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");

        Map<String, Object> claims = new HashMap<>();

        claims.put("id", 9527);
        claims.put("username", "liucangsong");
        claims.put("email", "liucangsong@163.com");

        Date expirationDate = new Date(System.currentTimeMillis() + 10 * 60 * 1000);

        System.out.println("过期时间：" + expirationDate);

        String secretKey = "da13*(!ahw2i%^!ksb($*&!4&?>$)kas<@jpsa";


        String jwt = Jwts.builder()
                //Header
                .setHeaderParams(headers)
                // Payload 载荷(内容)
                .setClaims(claims)
                //过期时间
                .setExpiration(expirationDate)
                // Signature 签名(撒盐)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                //整合
                .compact();
        System.out.println(jwt);
    }



    @Test
    public void testParse(){
        String jwt="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6OTUyNywiZXhwIjoxNjY1NTY4NTYzLCJlbWFpbCI6ImxpdWNhbmdzb25nQDE2My5jb20iLCJ1c2VybmFtZSI6ImxpdWNhbmdzb25nIn0.rRcvzw32Q1vu-0O7sDjIPfhetAhx2PEcnCzO_QjWcuk";
        Claims claims=Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(jwt).getBody();
        Object id=claims.get("id");
        Object username=claims.get("username");
        Object email=claims.get("email");
        Object phone=claims.get("phone");
        System.out.println(id);
        System.out.println(username);
        System.out.println(email);
        System.out.println(phone);

    }

}
