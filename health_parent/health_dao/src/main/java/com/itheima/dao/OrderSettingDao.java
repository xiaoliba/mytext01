package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/21 16:54
 */
public interface OrderSettingDao {
    /**
     * 检查此数据（日期）是否存在
     * @param orderDate
     * @return
     */
    long findCountByOrderDater(Date orderDate);

    /**
     * 更新预约设置数据
     * @param orderSetting
     */
    void update(OrderSetting orderSetting);

    /**
     * 增加预约设置信息
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 根据月份查询预约信息
     * @param map
     * @return
     */
    List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);

    /**
     * 根据日期查询预约当天的预约信息
     * @param date
     * @return
     */
    OrderSetting getOrderSettingByOrderDate(Date date);

    /**
     * 更新以预约人数
     * @param orderSetting
     */
    void updateReservations(OrderSetting orderSetting);
}
