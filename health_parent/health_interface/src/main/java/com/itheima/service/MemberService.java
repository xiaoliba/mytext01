package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;
import java.util.Map;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/24 20:24
 */
public interface MemberService {
    /**
     * 根据手机号查询会员信息
     * @param telephone
     * @return
     */
    Member findTelephone(String telephone);

    /**
     * 注册会员
     * @param member
     */
    void add(Member member);

    /**
     * 会员数据统计
     * @param months
     * @return
     */
    List<Integer> findMemberCountByMonth(List<String> months);

    List<Integer> findMemberCountByMonth2(List<String> months);

    /**
     * 套餐预约数据分析
     * @return
     */
    List<Map<String, Object>> fingPackageCount();
}
