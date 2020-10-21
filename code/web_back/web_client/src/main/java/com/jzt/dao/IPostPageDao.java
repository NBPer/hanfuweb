package com.jzt.dao;

import com.jzt.entity.PostPageEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/10/18 22:11
 */
@Repository("postPageDao")
public interface IPostPageDao {

    @Select("select * from tb_postpage")
    List<PostPageEntity> findAll();

    @Select("select * from tb_postpage tp where id=#{id}")
    @Results(id = "postPageMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "discription", property = "discription"),
            @Result(column = "id", property = "cover_id", javaType=com.jzt.entity.PhotoEntity.class,
                    one = @One(select = "com.jzt.dao.IPhotoDao.findById")),
            @Result(column = "look_count", property = "look_count"),
            @Result(column = "collect_count", property = "collect_count"),
            @Result(column = "create_time", property = "create_time"),
            @Result(column = "id", property = "user_id", javaType=com.jzt.entity.UserEntity.class,
                    one = @One(select = "com.jzt.dao.IUserDao.findById")),
            @Result(column = "imput_time", property = "imput_time"),
            //定义一对多的关系映射，实现对照片实体类photo的封装
            //        @Many注解用于一对多或多对多查询使用
            //        select属性指定内容：查询照片的唯一标识
            //        column属性指定内容：用户根据id查询照片实体类photo是所需要的参数
            //        fetchType属性指定内容:指定延时查询FetchType.LAZY或立即查询FetchType.EAGER
            @Result(column = "id", property = "photoEntities",
                    many = @Many(select = "com.jzt.dao.IPhotoDao.findByPostPageId"))//, fetchType = FetchType.LAZY//关掉懒加载不然会报错
    })
    PostPageEntity findById(Integer id);

}
