package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/18 16:05
 */
public interface CheckGroupDao {

    /**
     * 根据检查组id查询所有的检查项id
     * @param id
     * @return
     */
    List<Integer> getCheckitemIdByCheckgroupId(int id);

    /**
     * 添加检查组
     * @param checkGroup
     * @return
     */
    void add(CheckGroup checkGroup);

    /**
     * 设置检查组和检查项的关联关系
     * @param checkgroupId
     * @param checkitemIds
     */
    void setCheckGroupAndCheckItem(@Param("checkgroupId") Integer checkgroupId, @Param("checkitemIds")List<Integer> checkitemIds);

    /**
     * 查询全部检查组信息
     * @param queryString
     * @return
     */
    Page<CheckGroup> findAllData(String queryString);

    /**
     * 根据id查询检查组
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 删除关联
     * @param id
     */
    void deleteAssociation(int id);

    /**
     * 更新检查组
     * @param checkGroup
     */
    void update(CheckGroup checkGroup);

    /**
     * 根据id删除检查组
     * @param id
     */
    void deleteById(int id);

    /**
     * 查询与表t_CheckGroupCheckItem中的关联数
     * @param id
     * @return
     */
    int findgroupIdByCheckGroupCheckItem(int id);

    /**
     * 查询与表t_PackageCheckGroup中的关联数
     * @param id
     * @return
     */
    int findgroupIdByPackageCheckGroup(int id);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();
}
