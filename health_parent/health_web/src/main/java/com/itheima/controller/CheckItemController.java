package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/17 16:16
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    /**
     *add
     * @param checkItem
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")//权限校验
    public Result add(@RequestBody CheckItem checkItem){
        checkItemService.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
//    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        // 调用服务 分页查询
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        // 返回给页面
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }

    /**
     * 根据id删除检查项
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    public Result deleteById(Integer id){
        checkItemService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 根据id查询检查项
     * @param id
     * @return
     */
    @GetMapping("/findById")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findById(Integer id){
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    /**
     * 修改检查项
     * @param checkItem
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    public Result update(@RequestBody CheckItem checkItem){
        checkItemService.update(checkItem);
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 检查code值是否存咋
     * @param code
     * @return
     */
    @GetMapping("/findByCode")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findByCode(String code){
        boolean flag = checkItemService.findByCode(code);
        return new Result(flag,flag?"该code已存在":"");
    }

    /**
     * 查询所有检查项
     * @return
     */
    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findAll(){
        List<CheckItem>  checkItemList = checkItemService.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
    }
}
