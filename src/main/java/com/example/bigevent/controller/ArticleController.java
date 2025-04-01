package com.example.bigevent.controller;

import com.example.bigevent.pojo.Result;
import com.example.bigevent.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @GetMapping("/list")
    public Result<String> list(){
//  @RequestHeader(name="wza", required = false) String token, HttpServletResponse response      // 如果请求头中没有token，直接返回未登录的JSON响应
//        if (token == null || token.isEmpty())  {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return Result.error(" 用户未登录无法访问");
//        }
//
//        // 验证token
//        try {
//            Map<String, Object> claims = JwtUtil.parseToken(token);
//            return Result.success(" 文章数据");
//        } catch (Exception e) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return Result.error(" 用户未登录无法访问");
//        }
        return Result.success(" 文章数据");
    }
}
