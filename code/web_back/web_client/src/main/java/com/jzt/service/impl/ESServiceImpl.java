package com.jzt.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jzt.entity.PostPageEntity;
import com.jzt.service.IESService;
import com.jzt.util.CommonUtil;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/11/5 22:25
 */
@Service("esService")
public class ESServiceImpl implements IESService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public boolean savePostPageEntity(PostPageEntity postPageEntity, Integer id) throws IOException {
        IndexRequest request = new IndexRequest("post_page").id(String.valueOf(id)).source(CommonUtil.beanToMap(postPageEntity));
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(JSONObject.toJSON(response));
        return false;
    }

    @Override
    public PageInfo<PostPageEntity> findAllByPage(int currPage, int pageSize) {
        return null;
    }

    @Override
    public List<PostPageEntity> findPostPage(Integer id) {
        return null;
    }


}
