package com.jzt.es;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/11/14 17:31
 */
@Service("esService")
public class ESService {

//    @Autowired
//    private RestHighLevelClient restHighLevelClient;

//    public boolean savePostPageEntity(PostPageEntity postPageEntity, Integer id) throws IOException {
//        IndexRequest request = new IndexRequest("post_page").id(String.valueOf(id)).source(CommonUtil.beanToMap(postPageEntity));
//        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
//        System.out.println(JSONObject.toJSON(response));
//        return false;
//    }
}
