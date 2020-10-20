package com.jzt.dao;

import com.jzt.entity.UserEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 功能描述：
* @Author: sj
* @Date: 2020/10/15 20:30
*/
@Repository("userDao")
public interface IUserDao {

    @Select("select * from tb_user")
    List<UserEntity> findAll();

    @Select("select * from tb_user where id = #{id}")
    UserEntity findById(Integer id);

    @Insert("insert into tb_user(name, age) values (#{name}, #{age})")
    void save(UserEntity user);

    @Update("update tb_user set name=#{name}, age=#{age} where id=#{id}")
    void update(UserEntity user);

    @Delete("delete from tb_user where id=#{id}")
    void delete(Integer id);
}
