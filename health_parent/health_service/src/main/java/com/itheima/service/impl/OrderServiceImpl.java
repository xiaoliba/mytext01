package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/24 16:17
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Override
    @Transactional
    public Result order(Map<String, String> map) throws Exception {
        //1.判断当前日期是否设置了预约
        String orderDate = map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        //调用服务，根据日期查询预约信息是否存在
        OrderSetting orderSetting = orderSettingDao.getOrderSettingByOrderDate(date);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //2.判断当前日期预约是否已满
        if (orderSetting.getNumber() <= orderSetting.getReservations()) {
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        //3.判断是否是会员
        String telephone = map.get("telephone");
        //调用服务，查询会员是否存在
        Member member = memberDao.findTelephone(telephone);
        //是会员，避免重复预约
        if (member != null) {
            Order order = new Order(member.getId(),date,null,null,Integer.valueOf(map.get("packageId")));
            //调用服务，查询订单信息
            List<Order> orderList = orderDao.findByCondition(order);
            if (orderList != null & orderList.size() > 0) {
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else {//不是会员，为其注册成为会员
            member = new Member();
            member.setName(map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard(map.get("idCard"));
            member.setSex(map.get("sex"));
            member.setRegTime(new Date());
            //调用服务。添加会员
            memberDao.add(member);
        }
        //4.进行预约
        orderSetting.setReservations(orderSetting.getReservations()+1);
        //更新预约信息，以预约人数+1
        orderSettingDao.updateReservations(orderSetting);

        //保存预约信息到预约表
        Order order = new Order(
                member.getId(),
                date,
                map.get("orderType"),
                Order.ORDERSTATUS_NO,
                Integer.valueOf(map.get("packageId")));
        //调用服务，添加预约信息到order中
        orderDao.add(order);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    /**
     * 根据id查询预约信息，
     * @param id 预约id
     * @return 预约信息map集合
     */
    @Override
    public Map<String, Object> findById(Integer id) throws Exception {
        Map<String, Object> map = orderDao.findById(id);
        if(map != null){
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
        }
        return map;
    }
}
