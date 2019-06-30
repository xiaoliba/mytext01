package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import com.itheima.util.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/20 15:39
 */
@RestController
@RequestMapping("/package")
public class PackageController {
    @Reference
    private PackageService packageService;

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        //获取原始文件名
        String originalFilename = imgFile.getOriginalFilename();
        //获取文件名后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //创建新的随机文件名
        String newFileName = UUID.randomUUID() + suffix;
        try {
            QiNiuUtil.uploadViaByte(imgFile.getBytes(),newFileName);
            //把上传成功的图片文件名存入Jedis，用于定时清理垃圾文件
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
        //分别获取图片的地址+文件名,存放到map中
        Map<String,String> imgMap = new HashMap<>();
        imgMap.put("domain",QiNiuUtil.DOMAIN);
        imgMap.put("imgFileName",newFileName);
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,imgMap);
    }

    /**
     * 添加套餐
     * @param pack 套餐对象
     * @param checkgroupIds 关联id集合对象
     * @return Result
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('PACKAGE_ADD')")
    public Result add(@RequestBody Package pack,@RequestParam List<Integer> checkgroupIds){
        packageService.add(pack,checkgroupIds);
        //把存入数据库的图片文件名存入Jedis
        if (pack.getImg() != null && pack.getImg().length() > 0){
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pack.getImg());
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页查询
     * @param queryPageBean 查询对象
     * @return
     */
    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('PACKAGE_QUERY')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Package> pageResult = packageService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
    }

    /**
     * 根据套餐id查询package数据
     * @param id
     * @return
     */
    @GetMapping("/findById")
    @PreAuthorize("hasAuthority('PACKAGE_QUERY')")
    public Result findById(Integer id){
        Package pkg = packageService.findById(id);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, pkg);
    }

    /**
     * 根据套餐id查询关联关系
     * @param id
     * @return
     */
    @GetMapping("/getCheckGroupIdByPackageId")
    @PreAuthorize("hasAuthority('PACKAGE_QUERY')")
    public Result getCheckGroupIdByPackageId(Integer id){
        List<Integer> ids = packageService.getCheckGroupIdByPackageId(id);
        return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,ids);
    }

    /**
     * 更新套餐数据
     * @param pkg
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('PACKAGE_EDIT')")
    public Result update(@RequestBody Package pkg,@RequestParam List<Integer> checkgroupIds){
        packageService.update(pkg,checkgroupIds);
        //把存入数据库的图片文件名存入Jedis
        if (pkg.getImg() != null && pkg.getImg().length() > 0){
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pkg.getImg());
        }
        return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    /**
     * 删除套餐
     * @param id
     * @return
     */
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('PACKAGE_DELETE')")
    public Result delete(int id){
        int count = packageService.delete(id);
        if (count > 0) {
            return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
        }else {
            return new Result(true, MessageConstant.DELETE_ERROR);
        }
    }
}
