package com.jzt.dao;

import com.jzt.entity.PhotoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/10/18 22:34
 */
@Repository("photoDao")
public interface IPhotoDao {

    @Select("select * from tb_photo where id = #{id}")
    PhotoEntity findById(Long id);

    @Select("select * from tb_photo where postpage_id = #{postpage_id}")
    List<PhotoEntity> findByPostPageId(Integer postpage_id);

    @Insert({ "<script>",
            "insert into tb_tag(name) ",
            "<foreach collection='photoEntityList' index='index' item='item' separator=','>",
            "(#{item.name,jdbcType=VARCHAR})",
            "</foreach>",
            "</script>" })
    int[] batchInsert(@Param(value="photoEntityList") List<PhotoEntity> photoEntityList);
}
