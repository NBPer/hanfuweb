package com.jzt.dao;

import com.jzt.entity.TagEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 功能描述：
* @Author: sj
* @Date: 2020/10/15 20:30
*/
@Repository("tagDao")
public interface ITagDao {

    @Select("select * from tb_tag")
    List<TagEntity> findAll();

    @Select("select * from tb_tag where id = #{id}")
    TagEntity findById(Integer id);

    @Insert("insert into tb_tag(name) values (#{name})")
    void save(TagEntity tag);

    @Insert({ "<script>",
            "insert into tb_tag(name) values ",
            "<foreach collection='tagList' index='index' item='item' separator=','>",
            "(#{item.name,jdbcType=VARCHAR})",
            "</foreach>",
            "</script>" })
    void batchInsert(@Param(value="tagList") List<TagEntity> tagList);

    @Update("update tb_user set name=#{name}, age=#{age} where id=#{id}")
    void update(TagEntity user);

    @Delete("delete from tb_user where id=#{id}")
    void delete(Integer id);
}
