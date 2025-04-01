package com.example.bigevent.mapper;


import com.example.bigevent.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface User2Mapper {
    //根据用户名查询用户
    @Select("select * from user2 where username=#{username}")
    User findByUserName(String username);
    //添加普通用户
    @Insert("insert into user2(username,password,create_time,update_time)"+"values(#{username},#{password},now(),now())")
    void add(String username, String password);
}
