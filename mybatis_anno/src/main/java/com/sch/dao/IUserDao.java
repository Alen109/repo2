package com.sch.dao;

import com.sch.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@CacheNamespace(blocking = true)//开启二级缓存的使用
public interface IUserDao {


    /**
     * 查询所有用户
     * 别的方法可以通过id=userMap来引用此对应关系
     * @return
     */
    @Select("select * from user")
    @Results(id = "userMap" ,value = {
            @Result(id=true,property = "userId",column = "id"),
            @Result(property = "userName",column = "username"),
            @Result(property = "userAddress",column = "address"),
            @Result(property = "userSex",column = "sex"),
            @Result(property = "userBirthday",column = "birthday"),
            @Result(property = "accounts",column = "id",many = @Many(select = "com.sch.dao.IAccountDao.findByUid",fetchType = FetchType.LAZY))
    })
    List<User> findAll();


    /**
     * 通过id查询用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    @ResultMap(value = {"userMap"})
    User findById(Integer id);
}
