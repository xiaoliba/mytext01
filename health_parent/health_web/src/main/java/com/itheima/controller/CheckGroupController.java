package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/18 15:58
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    private static final Logger log = LoggerFactory.getLogger(CheckGroupController.class);

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('CHECKGROUP_ADD')")
    public Result add(@RequestBody CheckGroup checkGroup, @RequestParam List<Integer> checkitemIds){
        log.info("进入CheckGroupController的add方法");
        checkGroupService.add(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
//    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        log.info("进入CheckGroupController的findPage方法");
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    /**
     * 根据id查询检查组
     * @param id
     * @return
     */
    @GetMapping("/findById")
    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")
    public Result findById(int id){
        log.info("进入CheckGroupController的findBuId方法");
        log.debug("传入的参数值,id="+id);
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    /**
     * 根据检查组id查询所有的检查项id
     * @param id
     * @return
     */
    @GetMapping("/getCheckitemIdByCheckgroupId")
    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")
    public Result getCheckitemIdByCheckgroupId(int id){
        log.info("进入CheckGroupController的getCheckitemIdByCheckgroupId方法");
        log.debug("传入的参数值,id="+id);
        List<Integer> checkitemIds = checkGroupService.getCheckitemIdByCheckgroupId(id);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkitemIds);
    }

    /**
     * 更新检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('CHECKGROUP_EDIT')")
    public Result update(@RequestBody CheckGroup checkGroup, @RequestParam List<Integer> checkitemIds){
        log.info("进入CheckGroupController的update方法");
        checkGroupService.update(checkGroup,checkitemIds);
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 删除检查组
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    @PreAuthorize("hasAuthority('CHECKGROUP_DELETE')")
    public Result deleteById(int id){
        log.info("进入CheckGroupController的delete方法");
        log.debug("传入的参数值,id="+id);
        try {
            checkGroupService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_ERROR);
        }
    }

    /**
     * 查询所有检查组
     * @return
     */
    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")
    public Result findAll(){
        log.info("进入CheckGroupController的findAll方法");
        List<CheckGroup> checkGroupList = checkGroupService.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupList);
    }
}
