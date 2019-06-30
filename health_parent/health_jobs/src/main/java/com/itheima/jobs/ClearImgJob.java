package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.util.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.logging.SimpleFormatter;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/20 20:43
 */
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 清理图片
     */
    public void doJob(){
        System.out.println("清除上传图片缓存,现在时间：" + (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())));
        long startTime = System.currentTimeMillis();

        Set<String> picNeed2Delete = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(null != picNeed2Delete && picNeed2Delete.size()> 0) {
            String[] picNeed2DeleteArr = picNeed2Delete.toArray(new String[]{});
            QiNiuUtil.removeFiles(picNeed2DeleteArr);
            // 清除所有上传图片的缓存
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,picNeed2DeleteArr);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("总共耗时:"+(endTime-startTime));
    }

    //清理图片
    public void clearImg() {
        //1.获取reids缓存数据集合，计算set的差值
        Set<String> sdiffImgSet = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //2.遍历集合
        for (String sdiffImg : sdiffImgSet) {
            //3.删除
            //3.1 删除七牛空间上的所有非保存到数据库的图片缓存
            QiNiuUtil.removeFiles(sdiffImg);
            //3.2删除redis集合里面的所有非保存到数据库的图片缓存
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,sdiffImg);
        }
    }
}
