package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiContext;
import org.jxls.util.JxlsHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/27 15:21
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;

    @Reference
    private ReportService reportService;

    /**
     * 获取会员每年的增长信息
     * @return
     */
    @GetMapping("/getMemberReport")
    public Result getMemberReport(){
        //根据当前日期获取前一年的月份集合
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);//获得当前日期之前12个月的日期

        List<String> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            months.add(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()));
        }

        //根据月份集合，获取每月统计人数集合
        List<Integer> memberCount = memberService.findMemberCountByMonth(months);
//        List<Integer> memberCount = memberService.findMemberCountByMonth2(months);

        Map<String, Object> map = new HashMap<>();
        map.put("months",months);
        map.put("memberCount",memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }

    /**
     * 套餐预约数据分析
     * @return
     */
    @GetMapping("/getPackageReport")
    public Result getPackageReport(){
        List<Map<String,Object>> list = memberService.fingPackageCount();

        Map<String,Object> map = new HashMap<>();
        map.put("packageCount",list);

        List<String> packageNames = new ArrayList<>();
        for (Map<String, Object> m : list) {
            String name = (String) m.get("name");
            packageNames.add(name);
        }
        map.put("packageNames",packageNames);
        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
    }

    /**
     *获得运营统计数据
     * @return
     */
    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        Map<String,Object> map = null;
        try {
            map = reportService.getBusinessReportData();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
        return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
    }

    /**
     * 使用模板导出表格
     * @return
     */
    @GetMapping("/exportBusinessReport2")
    public Result exportBusinessReport2(HttpServletRequest request, HttpServletResponse response){
        //远程调用报表服务获取报表信息
        Map<String,Object> map = reportService.getBusinessReportData();

        //获得Excel模板文件的绝对路径
        String templateRealPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template2.xlsx";

        // 数据模型
        Context context = new PoiContext();
        context.putVar("obj", map);
        try {
            response.setContentType("application/vnd.ms-excel");
            String filename = new String("报表.xlsx".getBytes(),"ISO-8859-1");
//            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            response.setHeader("content-Disposition", "attachment;filename="+filename);
            // 把数据模型中的数据填充到文件中
                JxlsHelper.getInstance().processTemplate(new FileInputStream(new File(templateRealPath)),response.getOutputStream(),context);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_FAIL,null);
        }
    }

    /**
     * 导出表格
     * @return
     */
    @GetMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        try {
            //远程调用报表服务获取报表信息
            Map<String,Object> map = reportService.getBusinessReportData();

            //取出返回结果，将结果写入Excel中
            String reportDate = (String) map.get("reportDate");
            Integer todayNewMember = (Integer) map.get("todayNewMember");
            Integer totalMember = (Integer) map.get("totalMember");
            Integer thisWeekNewMember = (Integer) map.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) map.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) map.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) map.get("thisWeekOrderNumber");
            Integer todayVisitsNumber = (Integer) map.get("todayVisitsNumber");
            Integer thisMonthOrderNumber = (Integer) map.get("thisMonthOrderNumber");
            Integer thisWeekVisitsNumber = (Integer) map.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) map.get("thisMonthVisitsNumber");
            List<Map> hotPackage = (List<Map>) map.get("hotPackage");

            //获得Excel模板文件的绝对路径
            String templateRealPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";

            //读取模板文件，创建Excel表格对象
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(templateRealPath)));

            XSSFSheet sheetAt = workbook.getSheetAt(0);
            XSSFRow row = sheetAt.getRow(2);
            row.getCell(5).setCellValue(reportDate);//日期

            row = sheetAt.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//今天新增会员数
            row.getCell(7).setCellValue(totalMember);//会员总数

            row = sheetAt.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheetAt.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheetAt.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheetAt.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for (Map pkg : hotPackage) {//热门套餐
                String name = (String) pkg.get("name");
                Long package_count = (Long) pkg.get("package_count");
                BigDecimal proportion = (BigDecimal) pkg.get("proportion");
                row = sheetAt.getRow(rowNum++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(package_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            //通过输出流进行文件下载
            ServletOutputStream os = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition","attachmentl;filename=report.xlsx");
            workbook.write(os);

            os.flush();
            os.close();
            workbook.close();;
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_FAIL,null);
        }
    }
}
