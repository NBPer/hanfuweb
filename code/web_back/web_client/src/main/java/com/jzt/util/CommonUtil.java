package com.jzt.util;

import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/11/5 23:05
 */
public class CommonUtil {

    /**
     * 对象转map
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean){
        Map<String, Object> map = new HashMap<>();
        if(bean != null){
            BeanMap beanMap = BeanMap.create(bean);
            for(Object key : beanMap.keySet()){
                if (beanMap.get(key) != null){
                    map.put(key+"", beanMap.get(key));
                }
            }
        }
        return map;
    }
}
