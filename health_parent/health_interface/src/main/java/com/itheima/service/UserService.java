package com.itheima.service;

import com.itheima.pojo.User;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/26 16:48
 */
public interface UserService {
    User findByUsername(String username);
}
