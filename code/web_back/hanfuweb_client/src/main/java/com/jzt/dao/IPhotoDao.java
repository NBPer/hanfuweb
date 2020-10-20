package com.jzt.dao;

import com.jzt.entity.PhotoEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
}
