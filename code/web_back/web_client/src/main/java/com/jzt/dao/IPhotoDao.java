package com.jzt.dao;

import com.jzt.entity.PhotoEntity;
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
}
