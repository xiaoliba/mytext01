package com.itheima.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/26 16:44
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    private UserService userService;

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.远程调用用户服务，根据用户名查询用户信息
        User user = userService.findByUsername(username);
        //2.判断是否为空
        if (user == null) {
            return null;
        }
        //3.把用户名,数据库的密码,以及查询出来的权限访问,创建UserDetails对象返回
        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        roles.forEach(role -> {
            Set<Permission> permissions = role.getPermissions();
            permissions.forEach(permission -> {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            });
        });

        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
        return userDetails;
    }
}
