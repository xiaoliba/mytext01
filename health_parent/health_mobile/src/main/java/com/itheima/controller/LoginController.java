package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/24 20:13
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    /**
     * 登录校验
     * @param map
     * @return
     */
    @PostMapping("/check")
    public Result check(HttpServletResponse response, @RequestBody Map<String, String> map){
        //通过手机号获取redis中的code
        String telephone = map.get("telephone");
        String loginCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        //获取前端输入的code
        String validateCode = map.get("validateCode");
        if (validateCode == null) {
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
        if (loginCode == null || !validateCode.equals(loginCode)) {
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }else {
            //验证码输入正确 45 //判断当前用户是否为会员
            Member member = memberService.findTelephone(telephone);
            if (member == null) {
                //当前用户不是会员，自动完成注册
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
            //是会员，登录成功
            // 登录信息存入cookie
            Cookie cookie = new Cookie("login_member_telephone",telephone);
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24*7);
            response.addCookie(cookie);
        }
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
