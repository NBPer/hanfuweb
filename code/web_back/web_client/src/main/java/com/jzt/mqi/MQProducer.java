package com.jzt.mqi;

import com.alibaba.fastjson.JSON;
import com.jzt.cache.Cache;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/11/14 10:14
 */
@Component
public class MQProducer {

    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    private KafkaProducer<String, String> producer;

    public MQProducer(){
        Properties props = new Properties();
//        props.put("bootstrap.servers", "192.168.31.121:9092");
        props.put("bootstrap.servers", "182.61.149.221:9092");
//        // 等待所有副本节点的应答
//        props.put("acks", "all");
//        // 消息发送最大尝试次数
//        props.put("retries", 0);
//        // 一批消息处理大小
//        props.put("batch.size", 16384);
//        // 增加服务端请求延时
//        props.put("linger.ms", 1);
//        // 发送缓存区内存大小
//        props.put("buffer.memory", 33554432);
        props.put("serializer.class", "org.apache.kafka.common.serialization.StringSerializer");
        // key序列化
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value序列化
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(props);
        DataSyncTask syncTask = new DataSyncTask(producer);
        syncTask.start();
    }

    public void sendMsg(String topic, String key, String msg){
        try {
            logger.info("{MQProducer} send msg,"+"topic:"+topic+", key:"+key+", msg:"+msg);
            producer.send(new ProducerRecord<String, String>(topic, key, msg));
            producer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class DataSyncTask extends Thread {

        private KafkaProducer<String, String> producer;

        public DataSyncTask(KafkaProducer<String, String> producer){
            this.producer = producer;
        }

        @Override
        public void run(){
            while(true){
                try{
                    Map<String, Object> msgMap = Cache.queue.poll(2, TimeUnit.SECONDS);//等待2s,没有拿到数据就返回null
//                    Map<String, Object> msgMap = Cache.queue.take();//拿不到数据会阻塞
                    if(msgMap != null){
                        int i = 0;
                        String msg = JSON.toJSONString(msgMap);
//                        logger.info("{DataSyncTask} start get msg:"+msg);
                        sendMsg("DATA_SYNC", i+"", msg);
                        i++;
                    }
                    logger.info("{DataSyncTask} queue has no msg...");
                    Thread.sleep(5000);
                }catch (Exception e){
                    logger.error("{DataSyncTask} error", e);
                }
            }
        }
    }
}

