package com.jzt.service.impl;

import com.jzt.dao.IPostPageDao;
import com.jzt.dao.IUserDao;
import com.jzt.entity.PostPageEntity;
import com.jzt.entity.UserEntity;
import com.jzt.service.IPostPageService;
import com.jzt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 功能描述：
* @Author: sj
* @Date: 2020/10/15 20:32
*/
@Service("postPageService")
public class PostPageServiceImpl implements IPostPageService {

    @Autowired
    private IPostPageDao postPageDao;

    @Override
    public List<PostPageEntity> findAll() {
        return postPageDao.findAll();
    }

    @Override
    public PostPageEntity findById(Integer id) {
        return postPageDao.findById(id);
    }

    @Override
    public void save(PostPageEntity user) {

    }

    @Override
    public void update(PostPageEntity user) {

    }

    @Override
    public void delete(Integer id) {

    }
}
