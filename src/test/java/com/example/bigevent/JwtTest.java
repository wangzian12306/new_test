package com.example.bigevent;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testGen(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","测试");
        String token=JWT.create()
                .withClaim("user",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))//添加过期时间
                .sign(Algorithm.HMAC256("123456"));//指定算法秘钥
        System.out.println(token);
    }

    @Test
    public void testParse(){
        //模拟用户传递的token
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6Iua1i-ivlSJ9LCJleHAiOjE3NDIzMjk5Mzd9" +
                ".V7WRZ0jppZQ09MpBpdTmg6VI7epQdvtWJ1m4d9jk3qQ";
        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256("123456")).build();
        DecodedJWT decodedJWT=jwtVerifier.verify(token);//解析
        Map<String, Claim>claims=decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }
    //秘钥是指定的，都能防篡改，拿着载荷找源码
    //token过期了也会失效。我目前是半天
}
