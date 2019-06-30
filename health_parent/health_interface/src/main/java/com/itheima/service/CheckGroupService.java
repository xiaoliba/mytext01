package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/18 15:59
 */
public interface CheckGroupService {
    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    void add(CheckGroup checkGroup, List<Integer> checkitemIds);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
     * 根据id查询检查组
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 根据检查组id查询所有的检查项id
     * @param id
     * @return
     */
    List<Integer> getCheckitemIdByCheckgroupId(int id);

    /**
     * 更新检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    void update(CheckGroup checkGroup, List<Integer> checkitemIds);

    /**
     * 删除检查组
     * @param id
     * @return
     */
    void deleteById(int id);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();
}
