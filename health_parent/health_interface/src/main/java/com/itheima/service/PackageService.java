package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Package;

import java.util.List;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/20 15:42
 */
public interface PackageService {


    /**
     * 添加套餐
     * @param pack 套餐对象
     * @param checkgroupIds 关联id集合对象
     */
    void add(Package pack, List<Integer> checkgroupIds);

    /**
     * 分页查询
     * @param queryPageBean 查询对象
     * @return
     */
    PageResult<Package> findPage(QueryPageBean queryPageBean);

    /**
     * 根据套餐id查询package数据
     * @param id
     * @return
     */
    Package findById(Integer id);

    /**
     * 根据套餐id查询关联关系
     * @param id
     * @return
     */
    List<Integer> getCheckGroupIdByPackageId(Integer id);

    /**
     * 更新套餐数据
     * @param pkg
     * @param checkgroupIds
     */
    void update(Package pkg, List<Integer> checkgroupIds);

    /**
     * 删除套餐
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 获取所有套餐信息
     * @return
     */
    List<Package> findAll();

    /**
     * 根据id查询套餐信息
     * @param id
     * @return
     */
    Package findPackageById(int id);
}
