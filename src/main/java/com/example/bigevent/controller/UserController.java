package com.example.bigevent.controller;

import com.example.bigevent.pojo.Result;
import com.example.bigevent.pojo.User;
import com.example.bigevent.service.UserService;
import com.example.bigevent.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(String username,String password){

        if(username.length()>4 && username.length()<10){
           if(password.length()>4&&password.length()<10){
               //查询用户
               User u=userService.findByUserName(username);
               if(u==null){
                   userService.register(username,password);
                   return Result.success();
               }else {
                   return Result.error("用户名已被占用");

               }
           }else{
               return Result.error("密码长度在4到10之间");
           }


        }else{
            return Result.error("用户名长度在4到10之间");
        }


    };

    @PostMapping("/login")
    public Result<String> login(String username,String password) {
        User user = userService.findByUserName(username);
        if (user == null) {
            return Result.error("用户名错误");
        }
        if (user.getPassword().equals(password)) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());
            String token=JwtUtil .genToken(claims);
            return Result.success(token);
        } else {
            return Result.error("密码错误");
        }
    }
}
