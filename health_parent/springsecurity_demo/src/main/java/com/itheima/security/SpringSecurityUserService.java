package com.itheima.security;

import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/26 15:45
 */
public class SpringSecurityUserService implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String str) throws UsernameNotFoundException {
        //1.根据用户名查询数据库,获得User
        User user = findUserByUname(str);
        //2.判断是否为null
        if (user == null) {
            return null;
        }
        //3.把用户名,数据库的密码,以及查询出来的权限访问,创建UserDetails对象返回
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        list.add(new SimpleGrantedAuthority("add"));
        list.add(new SimpleGrantedAuthority("delete"));
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(str,user.getPassword(),list);
        return userDetails;
    }

    //模拟从数据库查询
    private User findUserByUname(String username) {
        if ("admin".equals(username)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(encoder.encode("123456"));
            return user;
        }
        return null;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("admin");
        System.out.println("encode:"+encode);
        String str = "$2a$10$zIFZQfyQKX8Wf43eAJZmEOncnWCkXPF3h768waNCuMUYIcyThk5a2";
        System.out.println(encode.equals(str));
    }
}
