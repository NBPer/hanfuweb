package com.jzt.dao;

import com.jzt.entity.UserEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public interface IUserDao {

    @Select("select * from user")
    List<UserEntity> findAll();

    UserEntity fineById(Integer id);

    void save(UserEntity test);

    void update(UserEntity test);

    void delete(Integer id);
}
