package com.jzt.dao;

import com.jzt.entity.HomePageEntity;
import org.apache.ibatis.annotations.*;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/10/22 22:01
 */
public interface IHomePageDao {

    @Select("select * from tb_homepage th where th.id=#{id}")
    @Results(id = "homePageMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "blogID", property = "blogID"),
            @Result(column = "city", property = "city"),
            @Result(column = "sign", property = "sign"),
            @Result(column = "regist_time", property = "regist_time"),
            @Result(column = "id", property = "postPageEntities",
                    many = @Many(select = "com.jzt.dao.IPostPageDao.findByHomePageId"))//, fetchType = FetchType.LAZY//关掉懒加载不然会报错
            }
        )
    HomePageEntity findById(Integer id);

}
