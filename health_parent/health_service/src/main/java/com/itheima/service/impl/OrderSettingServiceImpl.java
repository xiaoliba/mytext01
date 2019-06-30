package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/21 16:54
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 批量添加
     * @param orderSettingList
     */
    @Override
    @Transactional
    public void add(List<OrderSetting> orderSettingList) {
        if (orderSettingList != null && orderSettingList.size() > 0) {
            for (OrderSetting orderSetting : orderSettingList) {
                //检查此数据（日期）是否存在
                long count = orderSettingDao.findCountByOrderDater(orderSetting.getOrderDate());
                if (count > 0) {
                    //已经存在，执行更新操作
                    orderSettingDao.update(orderSetting);
                }else {
                    //不存在，执行添加操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    /**
     * 根据月份查询预约信息
     * @param date
     * @return
     */
    @Override
    public List<Map<String, Object>> getOrderSettingByMonth(String date) {
        String dateBegin = date + "-1";
        String dateEnd = date + "-31";
        Map<String,String> map = new HashMap<>();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        List<OrderSetting> orderSettingList = orderSettingDao.getOrderSettingByMonth(map);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettingList) {
            Map<String,Object> orderSettingMap = new HashMap<>();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());
            orderSettingMap.put("number",orderSetting.getNumber());
            orderSettingMap.put("reservations",orderSetting.getReservations());
            mapList.add(orderSettingMap);
        }
        return mapList;
    }

    /**
     * 根据时间修改预约人数
     * @param orderSetting
     */
    @Override
    public void setNumberByDate(OrderSetting orderSetting) {
        //检查此数据（日期）是否存在
        long count = orderSettingDao.findCountByOrderDater(orderSetting.getOrderDate());
        if (count > 0) {
            //已经存在，执行更新操作
            orderSettingDao.update(orderSetting);
        }else {
            //不存在，执行添加操作
            orderSettingDao.add(orderSetting);
        }
    }
}
