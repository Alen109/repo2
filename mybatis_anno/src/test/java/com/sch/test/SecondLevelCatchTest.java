package com.sch.test;

import com.sch.dao.IUserDao;
import com.sch.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class SecondLevelCatchTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;
    private SqlSessionFactory factory;

    @Before//用于在测试方法执行之前执行
    public void init()throws Exception{
        //1.读取配置文件，生成字节输入流
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取SqlSession对象
        sqlSession = factory.openSession(true);
        //4.获取dao的代理对象
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After//用于在测试方法执行之后执行
    public void destroy()throws Exception{
        //提交事务
        // sqlSession.commit();
        //6.释放资源

        in.close();
    }

    @Test
    public void testFindAll(){
        List<User> users = userDao.findAll();
        //注释掉之后就可以看到延迟加载的效果:只执行了select * from user，有需要的时候会执行select * from user where uid = ?
//        for (User user : users) {
//            System.out.println(user);
//        }
    }

    @Test
    public void testFindById(){
        User user = userDao.findById(42);
        System.out.println(user);
        sqlSession.close();//释放一级缓存

        SqlSession sqlSession1 = factory.openSession();//再次打开sqlSession
        IUserDao userDao1 = sqlSession1.getMapper(IUserDao.class);

        User user1 = userDao1.findById(42);
        System.out.println(user1);

        System.out.println(user == user1);


    }
}
