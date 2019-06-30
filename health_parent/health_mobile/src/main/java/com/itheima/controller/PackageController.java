package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import com.itheima.util.QiNiuUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/22 11:13
 */
@RestController
@RequestMapping("/package")
public class PackageController {

    @Reference
    private PackageService packageService;

    /**
     * 获取所有套餐信息
     * @return
     */
    @GetMapping("/getPackage")
    public Result getPackage(){
        List<Package> list = packageService.findAll();
        //设置图片路径
        if (list != null && list.size() > 0) {
            list.forEach(pkg->{
                pkg.setImg(QiNiuUtil.DOMAIN+"/"+pkg.getImg());
            });
        }
        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
    }

    /**
     * 根据id查询套餐及其关联信息
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        Package pkg = packageService.findById(id);
        if (pkg.getImg() != null) {
            pkg.setImg(QiNiuUtil.DOMAIN+"/"+pkg.getImg());
        }
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pkg);
    }

    /**
     * 根据id查询套餐信息
     * @param id
     * @return
     */
    @GetMapping("/findPackageById")
    public Result findPackageById(int id){
        Package pkg = packageService.findPackageById(id);
        if (pkg.getImg() != null) {
            pkg.setImg(QiNiuUtil.DOMAIN+"/"+pkg.getImg());
        }
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pkg);
    }
}
