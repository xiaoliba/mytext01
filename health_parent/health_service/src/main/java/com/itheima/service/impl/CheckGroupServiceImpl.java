package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/18 16:05
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, List<Integer> checkitemIds) {
        checkGroupDao.add(checkGroup);
        checkGroupDao.setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        if (queryPageBean.getQueryString() != null) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<CheckGroup> page = checkGroupDao.findAllData(queryPageBean.getQueryString());
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    /**
     * 根据id查询检查组
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 根据检查组id查询所有的检查项id
     * @param id
     * @return
     */
    @Override
    public List<Integer> getCheckitemIdByCheckgroupId(int id) {
        return checkGroupDao.getCheckitemIdByCheckgroupId(id);
    }

    /**
     * 更新检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, List<Integer> checkitemIds) {
        checkGroupDao.update(checkGroup);
        checkGroupDao.deleteAssociation(checkGroup.getId());
        if (checkitemIds != null && checkitemIds.size() > 0) {
            checkGroupDao.setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
        }
    }

    /**
     * 删除检查组
     * @param id
     * @return
     */
    @Override
    @Transactional
    public void deleteById(int id) {
        //删除前，需判断是否有关联关系
        //判断该条数据是否与表t_checkgroup_checkitem有关联
        if (checkGroupDao.findgroupIdByCheckGroupCheckItem(id) > 0){
            throw new RuntimeException("当前检查组被引用，不能删除");
        }
        //判断该条数据是否与表t_package_checkgroup有关联
        if (checkGroupDao.findgroupIdByPackageCheckGroup(id) > 0){
            throw new RuntimeException("当前检查组被引用，不能删除");
        }
        checkGroupDao.deleteById(id);
    }

    /**
     * 查询所有检查组
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
