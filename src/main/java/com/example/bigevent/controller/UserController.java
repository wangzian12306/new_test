package com.example.bigevent.controller;

import com.example.bigevent.pojo.Result;
import com.example.bigevent.pojo.ThreadLocalUtil;
import com.example.bigevent.pojo.User;
import com.example.bigevent.service.UserService;
import com.example.bigevent.service.User2Service;
import com.example.bigevent.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private User2Service user2Service;

    @PostMapping("/register")
    public Result register(String username,String password,int leixing){

        if(username.length()>4 && username.length()<10){
           if(password.length()>4&&password.length()<10){

               if(leixing==1){
               //查询用户
               User u=userService.findByUserName(username);
               if(u==null){
                   userService.register(username,password);
                   return Result.success();
               }else {
                   return Result.error("用户名已被占用");

               }}
               else if(leixing==2) {
                   //查询用户
                   User u = user2Service.findByUserName(username);
                   if (u == null) {
                       user2Service.register(username, password);
                       return Result.success();
                   } else {
                       return Result.error("用户名已被占用");
                   }
               }
               else{
                   return Result.error("类型错误");
               }

           }else{
               return Result.error("密码长度在4到10之间");
           }


        }else{
            return Result.error("用户名长度在4到10之间");
        }


    };

    @PostMapping("/login")
    public Result<String> login(String username,String password,int leixing) {
        if(leixing==1){
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
        }else if(leixing==2){
            User user = user2Service.findByUserName(username);
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
        }else{
            return Result.error("类型错误");
        }


    }

   @GetMapping("/userInfo")
    public Result<User> userInfo() {
       Map<String,Object>map = ThreadLocalUtil.get();
       String username=(String) map.get("username");
       User user=userService.findByUserName(username);
       return Result.success(user);
    }

}
