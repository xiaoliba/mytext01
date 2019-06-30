package com.itheima.dao;

import com.itheima.pojo.Role;

import java.util.Set;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/26 16:59
 */
public interface RoleDao {
    Set<Role> findByUserId(Integer id);
}
