package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PackageDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/20 15:42
 */
@Service(interfaceClass = PackageService.class)
public class PackageServiceImpl implements PackageService {
    @Autowired
    private PackageDao packageDao;

    /**
     * 添加套餐
     * @param pack 套餐对象
     * @param checkgroupIds 关联id集合对象
     */
    @Override
    @Transactional
    public void add(Package pack, List<Integer> checkgroupIds) {
        packageDao.add(pack);
        packageDao.setPackageCheckgroup(pack.getId(),checkgroupIds);
    }

    /**
     * 分页查询
     * @param queryPageBean 查询对象
     * @return
     */
    @Override
    public PageResult<Package> findPage(QueryPageBean queryPageBean) {
        if (queryPageBean.getQueryString() != null) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Package> page = packageDao.findAll(queryPageBean.getQueryString());
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    /**
     * 根据套餐id查询package数据
     * @param id
     * @return
     */
    @Override
    public Package findById(Integer id) {
        return packageDao.findById(id);
    }

    /**
     * 根据套餐id查询关联关系
     * @param id
     * @return
     */
    @Override
    public List<Integer> getCheckGroupIdByPackageId(Integer id) {
        return packageDao.getCheckGroupIdByPackageId(id);
    }

    /**
     * 更新套餐数据
     * @param pkg
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void update(Package pkg, List<Integer> checkgroupIds) {
        packageDao.update(pkg);
        packageDao.deleteAssociationById(pkg.getId());
        if (checkgroupIds != null && checkgroupIds.size() > 0) {
            packageDao.setPackageCheckgroup(pkg.getId(),checkgroupIds);
        }
    }

    /**
     * 删除套餐
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int delete(int id) {
        //查询当前套餐是否存在关联关系
        if (packageDao.findAssociationByCheckGroup(id) > 0) {
            throw new RuntimeException("当前检查组被引用，不能删除");
        }
        return packageDao.delete(id);
    }

    /**
     * 获取所有套餐信息
     * @return
     */
    @Override
    public List<Package> findAll() {
        List<Package> list = packageDao.findAllList();
        return list;
    }

    /**
     * 根据id查询套餐信息
     * @param id
     * @return
     */
    @Override
    public Package findPackageById(int id) {
        return packageDao.findPackageById(id);
    }
}
