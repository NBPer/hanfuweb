package com.jzt.entity;

import lombok.Data;

import java.util.List;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/10/18 22:02
 */
public class TagEntity {

    private Integer id;

    /**
     * 标签名称
     */
    private String name;

    private List<PostPageEntity> postPageEntities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //这里不能添加get方法，因为该对象与PostPageEntity相互引用，多对多。会造成递归调用，引起堆栈溢出（java.lang.StackOverflowError）
//    public List<PostPageEntity> getPostPageEntities() {
//        return postPageEntities;
//    }

    public void setPostPageEntities(List<PostPageEntity> postPageEntities) {
        this.postPageEntities = postPageEntities;
    }

    @Override
    public String toString() {
        return "TagEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", postPageEntities=" + postPageEntities +
                '}';
    }
}
