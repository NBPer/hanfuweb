package com.jzt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzt.cache.Cache;
import com.jzt.dao.IPhotoDao;
import com.jzt.dao.IPostPageDao;
import com.jzt.dao.ITagDao;
import com.jzt.dao.IUserDao;
import com.jzt.entity.PhotoEntity;
import com.jzt.entity.PostPageEntity;
import com.jzt.entity.TagEntity;
import com.jzt.entity.UserEntity;
import com.jzt.service.IPostPageService;
import com.jzt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* 功能描述：
* @Author: sj
* @Date: 2020/10/15 20:32
*/
@Service("postPageService")
public class PostPageServiceImpl implements IPostPageService {

    @Autowired
    private IPostPageDao postPageDao;

    @Autowired
    private ITagDao tagDao;

    @Autowired
    private IPhotoDao photoDao;

    @Autowired
    private Cache cache;

    @Override
    public List<PostPageEntity> findAll() {
        return postPageDao.findAll();
    }

    @Override
    public PostPageEntity findById(Integer id) {
        return postPageDao.findById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(PostPageEntity postPageEntity) throws InterruptedException {
        //保存标签信息
        List<TagEntity> tagEntityList = postPageEntity.getTagEntities();
        if(tagEntityList != null && tagEntityList.size() > 0){
            tagDao.batchInsert(tagEntityList);
        }
        //保存图片信息
        List<PhotoEntity> photoEntityList = postPageEntity.getPhotoEntities();
        if(photoEntityList != null && photoEntityList.size() > 0){
            int[] photoIds = photoDao.batchInsert(photoEntityList);
        }
        //保存post主页信息
        postPageEntity.setCreate_time(new Date());
        postPageDao.insert(postPageEntity);

        //将更新的数据放入缓存，等待被更新到es中
        Map<String, Object> stringObjectMap = JSON.parseObject(JSON.toJSONString(postPageEntity), new TypeReference<Map<String, Object>>(){});
        System.out.println(stringObjectMap);
        Cache.queue.offer(stringObjectMap);
        System.out.println(Cache.queue.size());
    }

    @Override
    public void update(PostPageEntity postPageEntity) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public PageInfo<PostPageEntity> findAllByPage(int currPage, int pageSize) {
        PageHelper.startPage(currPage, pageSize);
        List<PostPageEntity> postPageEntityList = postPageDao.findAllByPage();
        PageInfo<PostPageEntity> pageInfo = new PageInfo<>(postPageEntityList);
        return pageInfo;
    }
}
