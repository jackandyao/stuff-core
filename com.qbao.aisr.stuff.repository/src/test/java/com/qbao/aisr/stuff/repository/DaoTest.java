package com.qbao.aisr.stuff.repository;

import com.qbao.aisr.stuff.model.mysql.StuffCategory;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffCategoryDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author shuaizhihu
 * @createTime 2017/3/6 17:23
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring_repository.xml"})
public class DaoTest {

    @Autowired
    StuffCategoryDao stuffCategoryDao;


    @Test
    public void test(){
        List<StuffCategory> list = stuffCategoryDao.findStuffCategoryByLev(4);
        for(StuffCategory stuffCategory:list){
            System.out.println(stuffCategory.toString());
        }
    }
}
