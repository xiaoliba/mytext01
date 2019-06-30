package com.itheima.service;

import com.itheima.entity.Result;

import java.util.Map;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/24 16:16
 */
public interface OrderService {
    Result order(Map<String, String> map) throws Exception;

    Map<String, Object> findById(Integer id) throws Exception;
}
