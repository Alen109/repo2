package com.sch.dao;

import com.sch.domain.Account;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface IAccountDao {

    /**
     * 查询所有账户，并获取每个账户所属的用户信息
     * @return
     */

    @Select("select * from account")
    @Results(id = "accountMap",value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "uid",column = "uid"),
            @Result(property = "money",column = "money"),
            @Result(property = "user",column = "uid",one = @One(select="com.sch.dao.IUserDao.findById",fetchType = FetchType.EAGER) )//property对应的是要封装的user对象，用column这个字段去查，指向select语句
    })
    List<Account> findAll();

    @Select("select * from account where uid = #{id}")
    List<Account> findByUid(Integer id);
}
