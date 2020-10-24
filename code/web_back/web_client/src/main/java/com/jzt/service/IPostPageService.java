package com.jzt.service;

import com.github.pagehelper.PageInfo;
import com.jzt.entity.PostPageEntity;
import com.jzt.entity.UserEntity;

import java.util.List;

/**
* 功能描述：
* @Author: sj
* @Date: 2020/10/15 20:32
*/

public interface IPostPageService {
    
    List<PostPageEntity> findAll();

    PostPageEntity findById(Integer id);

    void save(PostPageEntity user);

    void update(PostPageEntity user);

    void delete(Integer id);

    PageInfo<PostPageEntity> findAllByPage(int currPage, int pageSize);
}
