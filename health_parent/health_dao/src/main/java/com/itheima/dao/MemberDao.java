package com.itheima.dao;

import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/24 16:17
 */
public interface MemberDao {
    /**
     * 根据手机号查询会员是否存在
     * @param telephone
     * @return
     */
    Member findTelephone(String telephone);

    /**
     * 添加会员
     * @param member
     */
    void add(Member member);

    /**
     * 会员数据统计
     * @param month
     * @return
     */
    Integer findMemberCountByMonth(String month);

    List<Integer> findMemberCountByMonth2(@Param("months") List<String> months);

    /**
     * 套餐预约数据分析
     * @return
     */
    List<Map<String, Object>> fingPackageCount();

    /**
     * //今日新增会员数
     * @param today
     * @return
     */
    Integer findMemberCountByDate(String today);

    /**
     * 总会员数
     * @return
     */
    Integer findMemberTotalCount();

    /**
     * 本周新增会员数
     * @param thisWeekMonday
     * @return
     */
    Integer findMemberCountBeforeDate(String thisWeekMonday);

    /**
     *本月新增会员数
     * @param firstDay4ThisMonth
     * @return
     */
    Integer findMemberCountAfterDate(String firstDay4ThisMonth);
}
