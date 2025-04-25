-- 创建数据库
CREATE DATABASE IF NOT EXISTS pet;

-- 使用pet数据库
USE pet;

-- 用户表
CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       favorites TEXT,
                       comments_posted TEXT,
                       following_list TEXT,
                       followers_list TEXT,
                       profile_picture VARCHAR(255),
                       account_status INT
);

-- 帖子表
CREATE TABLE posts (
                       post_id INT AUTO_INCREMENT PRIMARY KEY,
                       post_type INT,
                       title VARCHAR(30),
                       user_id INT,
                       likes INT AUTO_INCREMENT UNIQUE,
                       content TEXT,
                       images VARCHAR(255),
                       created_at DATETIME,
                       review_status INT
);

-- 评论表
CREATE TABLE comments (
                          comment_id INT AUTO_INCREMENT PRIMARY KEY,
                          user_id INT,
                          content VARCHAR(100),
                          post_id INT,
                          created_at DATETIME,
                          review_status INT
);

-- 领养表
CREATE TABLE adoptions (
                           post_id INT PRIMARY KEY,
                           user_id INT,
                           content TEXT,
                           budget_or_situation INT,
                           gender INT,
                           age INT,
                           occupation VARCHAR(30),
                           location VARCHAR(30),
                           adoption_method VARCHAR(20),
                           review_status INT
);

-- 送养表
CREATE TABLE fosterings (
                            post_id INT PRIMARY KEY,
                            user_id INT,
                            content TEXT,
                            budget INT,
                            images VARCHAR(255),
                            adoption_method VARCHAR(20),
                            review_status INT
);

-- 公告表
CREATE TABLE announcements (
                               announcement_id INT AUTO_INCREMENT PRIMARY KEY,
                               title VARCHAR(30),
                               tag VARCHAR(10),
                               content TEXT,
                               created_at DATETIME
);