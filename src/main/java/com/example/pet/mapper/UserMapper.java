package com.example.pet.mapper;
import com.example.pet.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    //根据用户名查询用户
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);
    //添加普通用户
    @Insert("insert into user(username,password,create_time,update_time)"+"values(#{username},#{password},now(),now())")
    void add(String username, String password);

    //更新用户信息
   // @Update("update user set nickname=#{nickname}, email=#{email}, update_time=#{updateTime}")
   // void update(User user);


    // 根据用户ID查询用户
    @Select("SELECT * FROM users WHERE user_id=#{userId}")
    User findByUserId(Integer userId);

    // 查询特定字段方法
    @Select("SELECT username FROM users WHERE user_id=#{userId}")
    String findUsernameById(Integer userId);

    @Select("SELECT favorites FROM users WHERE user_id=#{userId}")
    String findFavoritesById(Integer userId);

    @Select("SELECT comments_posted FROM users WHERE user_id=#{userId}")
    String findCommentsPostedById(Integer userId);

    @Select("SELECT following_list FROM users WHERE user_id=#{userId}")
    String findFollowingListById(Integer userId);

    @Select("SELECT followers_list FROM users WHERE user_id=#{userId}")
    String findFollowersListById(Integer userId);

    @Select("SELECT profile_picture FROM users WHERE user_id=#{userId}")
    String findProfilePictureById(Integer userId);

    // 更新操作方法
    // 更新用户全部信息
    @Update("UPDATE users SET username=#{username}, password=#{password}, favorites=#{favorites}, " +
            "comments_posted=#{commentsPosted}, following_list=#{followingList}, " +
            "followers_list=#{followersList}, profile_picture=#{profilePicture}, " +
            "account_status=#{accountStatus}, update_time=NOW() WHERE user_id=#{userId}")
    void update(User user);

    // 更新单个字段方法
    @Update("UPDATE users SET favorites=#{favorites}, update_time=NOW() WHERE user_id=#{userId}")
    void updateFavorites(@Param("userId") Integer userId, @Param("favorites") String favorites);

    @Update("UPDATE users SET comments_posted=#{commentsPosted}, update_time=NOW() WHERE user_id=#{userId}")
    void updateCommentsPosted(@Param("userId") Integer userId, @Param("commentsPosted") String commentsPosted);

    @Update("UPDATE users SET following_list=#{followingList}, update_time=NOW() WHERE user_id=#{userId}")
    void updateFollowingList(@Param("userId") Integer userId, @Param("followingList") String followingList);

    @Update("UPDATE users SET followers_list=#{followersList}, update_time=NOW() WHERE user_id=#{userId}")
    void updateFollowersList(@Param("userId") Integer userId, @Param("followersList") String followersList);

    @Update("UPDATE users SET account_status=#{accountStatus}, update_time=NOW() WHERE user_id=#{userId}")
    void updateAccountStatus(@Param("userId") Integer userId, @Param("accountStatus") Integer accountStatus);

    // 更新用户头像
    @Update("UPDATE users SET profile_picture=#{profilePicture}, update_time=NOW() WHERE user_id=#{userId}")
    void updateProfilePicture(@Param("userId") Integer userId, @Param("profilePicture") String profilePicture);

}