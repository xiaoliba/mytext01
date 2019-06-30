package com.itheima.dao;

import com.itheima.pojo.Permission;

import java.util.Set;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/26 16:59
 */
public interface PermissionDao {
    Set<Permission> findByRoleId(Integer id);
}
