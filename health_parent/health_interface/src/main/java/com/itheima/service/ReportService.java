package com.itheima.service;

import java.util.Map;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/27 20:20
 */
public interface ReportService {
    /**
     * 获得运营统计数据
     * Map数据格式：
     *      todayNewMember ‐> number
     *      totalMember ‐> number
     *      thisWeekNewMember ‐> number
     *      thisMonthNewMember ‐> number
     *      todayOrderNumber ‐> number
     *      todayVisitsNumber ‐> number
     *      thisWeekOrderNumber ‐> number
     *      thisWeekVisitsNumber ‐> number
     *      thisMonthOrderNumber ‐> number
     *      thisMonthVisitsNumber ‐> number
     *      hotPackage ‐> List<Package>
     * @return
     */
    Map<String, Object> getBusinessReportData();
}
