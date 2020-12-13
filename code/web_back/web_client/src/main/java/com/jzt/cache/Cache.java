package com.jzt.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/11/14 15:16
 */
@Component
public class Cache {
    public static BlockingQueue<Map<String, Object>> queue = new LinkedBlockingDeque<>(100);
}
