package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/24 16:17
 */
public interface OrderDao {
    List<Order> findByCondition(Order order);

    void add(Order order);

    Map<String, Object> findById(Integer id);

    /**
     * /今日预约数
     * @param today
     * @return
     */
    Integer findOrderCountByDate(String today);

    /**
     * 本月预约数
     * @param firstDay4ThisMonth
     * @return
     */
    Integer findOrderCountAfterDate(String firstDay4ThisMonth);

    /**
     * 今日到诊数
     * @param today
     * @return
     */
    Integer findVisitsCountByDate(String today);

    /**
     * 本月到诊数
     * @param firstDay4ThisMonth
     * @return
     */
    Integer findVisitsCountAfterDate(String firstDay4ThisMonth);

    /**
     * 热门套餐（取前4）
     * @return
     */
    List<Map> findHotPackage();
}
