package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/24 20:24
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    /**
     * 根据手机号查询会员信息
     * @param telephone
     * @return
     */
    @Override
    public Member findTelephone(String telephone) {
        return memberDao.findTelephone(telephone);
    }

    /**
     * 注册会员
     * @param member
     */
    @Override
    public void add(Member member) {
        if (member.getPassword() != null) {//密码加密
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    /**
     * 会员数据统计
     * @param months
     * @return
     */
    @Override
    public List<Integer> findMemberCountByMonth(List<String> months) {
        List<Integer> list = new ArrayList<>();
        for (String month : months) {
            month = month.replaceAll("\\.","-")+"-32";
            list.add(memberDao.findMemberCountByMonth(month));
        }
        return list;
    }

    @Override
    public List<Integer> findMemberCountByMonth2(List<String> months) {
        List<String> list = new ArrayList<>();
        for (String month : months) {
            month = month.replaceAll("\\.","-")+"-32";
            list.add(month);
        }
        List<Integer> members = memberDao.findMemberCountByMonth2(list);
        return members;
    }

    /**
     * 套餐预约数据分析
     * @return
     */
    @Override
    public List<Map<String, Object>> fingPackageCount() {
        return memberDao.fingPackageCount();
    }
}
