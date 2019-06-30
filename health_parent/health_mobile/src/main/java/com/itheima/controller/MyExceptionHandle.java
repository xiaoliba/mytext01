package com.itheima.controller;

import com.itheima.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/21 15:09
 */
@ControllerAdvice// 拦截controller
public class MyExceptionHandle {
    private static final Logger log = LoggerFactory.getLogger(MyExceptionHandle.class);

    /**
     * 自定义全局异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){
        log.error("出错了",e);
        return new Result(false,"系统忙，请稍后");
    }

    /**
     * 自定义全局运行时异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Result runtime(RuntimeException e){
        log.error("不符合要求....",e);
        return new Result(false,"系统忙，请稍后");
    }

    //还可设置其他全局异常处理器......
}
