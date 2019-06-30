package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/21 16:54
 */
public interface OrderSettingService {

    /**
     * 批量添加
     * @param orderSettingList
     */
    void add(List<OrderSetting> orderSettingList);

    /**
     * 根据月份查询预约信息
     * @param date
     * @return
     */
    List<Map<String, Object>> getOrderSettingByMonth(String date);

    /**
     * 根据时间修改预约人数
     * @param orderSetting
     */
    void setNumberByDate(OrderSetting orderSetting);
}
