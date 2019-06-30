package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/17 16:25
 */
public interface CheckItemDao {

    void add(CheckItem checkItem);

    Page<CheckItem> findAllByCondition(String queryString);

    void deleteById(Integer id);

    CheckItem findById(Integer id);

    void update(CheckItem checkItem);


    CheckItem findByCode(String code);

    List<CheckItem> findAll();

    int findCountByCheckItemId(Integer id);
}
