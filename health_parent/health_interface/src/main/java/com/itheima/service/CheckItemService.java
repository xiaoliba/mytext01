package com.itheima.service;

import com.github.pagehelper.Page;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/17 16:20
 */
public interface CheckItemService {
    /**
     * 新增检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    CheckItem findById(Integer id);

    void update(CheckItem checkItem);

    boolean findByCode(String code);

    List<CheckItem> findAll();
}
