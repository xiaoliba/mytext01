package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Package;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/20 15:42
 */
public interface PackageDao {
    /**
     * 添加套餐
     * @param pack
     */
    void add(Package pack);

    /**
     * 添加检查组与套餐的关联关系
     * @param packageId
     * @param checkgroupIds
     */
    void setPackageCheckgroup(@Param("packageId") Integer packageId, @Param("checkgroupIds") List<Integer> checkgroupIds);

    /**
     * 分页-查询所有
     * @param queryString 查询条件
     * @return
     */
    Page<Package> findAll(String queryString);

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
     */
    void update(Package pkg);

    /**
     * 删除套餐关联数据
     * @param id
     */
    void deleteAssociationById(Integer id);

    int findAssociationByCheckGroup(int id);

    int delete(int id);

    /**
     * 获取所有套餐信息
     * @return
     */
    List<Package> findAllList();

    /**
     * 根据id查询套餐信息
     * @param id
     * @return
     */
    Package findPackageById(int id);
}
