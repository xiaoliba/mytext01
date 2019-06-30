package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.util.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/23 20:59
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 提交预约信息
     * @param map
     * @return
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String, String> map){
        //通过手机号获取redis中的code
        String telephone = map.get("telephone");
        String redisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        //获取前端输入的code
        String validateCode = map.get("validateCode");
        //验证码校验
        if (validateCode != null) {
            if (redisCode == null || !validateCode.equals(redisCode)) {
                return new Result(false,MessageConstant.VALIDATECODE_ERROR);
            }
        }else {
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }

        Result result =null;
        //设置预约体检类型
        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        try {
            result = orderService.order(map);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        if (result.isFlag()) {
            //预约成功，发送短信通知
            String orderDate = (String)map.get("orderDate");
            try {
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,telephone);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 根据id查询预约信息，
     * @param id 预约id
     * @return 预约信息map集合
     */
    @GetMapping("/findById")
    public Result findById(Integer id){
        Map<String,Object> orderMap = null;
        try {
            orderMap = orderService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_ORDER_FAIL,null);
        }
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,orderMap);
    }
}
