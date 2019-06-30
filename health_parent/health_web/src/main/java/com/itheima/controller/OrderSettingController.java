package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.util.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/21 16:41
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    private static final Logger log = LoggerFactory.getLogger(OrderSettingController.class);

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 预约信息批量信息添加
     * @param excelFile
     * @return
     */
    @RequestMapping("/uploadFile")
    @PreAuthorize("hasAuthority('ORDERSETTING')")
    public Result uploadFile(@RequestParam("excelFile") MultipartFile excelFile){
        log.info("正在执行ordersetting中的uploadFile方法");
        try {
            List<String[]> readExcelList = POIUtils.readExcel(excelFile);
            if (readExcelList != null && readExcelList.size() > 0) {
                List<OrderSetting> orderSettingList = new ArrayList<>();
                for (String[] strings : readExcelList) {
                    OrderSetting orderSetting = new OrderSetting(new Date(strings[0]),Integer.valueOf(strings[1]));
                    orderSettingList.add(orderSetting);
                }
                //添加预约数据
                orderSettingService.add(orderSettingList);
            }
        } catch (IOException e) {
            log.debug("发生了IO异常......",e);
            return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        log.info("ordersetting中的uploadFile方法执行完毕");
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    /**
     * 根据月份查询预约信息
     * @param date
     * @return
     */
    @GetMapping("/getOrderSettingByMonth")
//    @PreAuthorize("hasAuthority('ORDERSETTING')")
    public Result getOrderSettingByMonth(String date){
        List<Map<String,Object>> mapList = orderSettingService.getOrderSettingByMonth(date);
        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,mapList);
    }

    /**
     * 根据时间修改预约人数
     * @param orderSetting
     * @return
     */
    @PostMapping("/setNumberByDate")
    @PreAuthorize("hasAuthority('ORDERSETTING')")
    public Result setNumberByDate(@RequestBody OrderSetting orderSetting){
        orderSettingService.setNumberByDate(orderSetting);
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
    }
}
