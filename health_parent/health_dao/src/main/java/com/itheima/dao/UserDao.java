package com.itheima.dao;

import com.itheima.pojo.User;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/26 16:50
 */
public interface UserDao {
    User findByUsername(String username);
}
