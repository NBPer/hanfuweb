package com.jzt.service;

import com.github.pagehelper.PageInfo;
import com.jzt.entity.PostPageEntity;

import java.io.IOException;
import java.util.List;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/11/5 22:22
 */
public interface IESService {

    PageInfo<PostPageEntity> findAllByPage(int currPage, int pageSize);

    List<PostPageEntity> findPostPage(Integer id);

    boolean savePostPageEntity(PostPageEntity postPageEntity, Integer id) throws IOException;
}
