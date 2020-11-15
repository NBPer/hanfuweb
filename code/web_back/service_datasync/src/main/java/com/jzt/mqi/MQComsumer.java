package com.jzt.mqi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/11/14 7:54
 */
@Component
public class MQComsumer {

    private KafkaConsumer<String, String> comsumer;

    private Environment env;

    @PostConstruct
    public void init() throws Exception{
        System.out.println("{MQComsumer} init .");
        initComsumer();
    }

    private void initComsumer() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "192.168.31.121:9092");//env.getProperty("192.168.31.121:9092")
        //每个消费者分配独立的组号
        props.put("group.id", "TEST_GROUP");//env.getProperty("TEST_GROU
        //为true时，comsumer自动提交偏移量offset
        props.setProperty("auto.commit.enabler", "true");
        //自动提交的时间间隔
        props.setProperty("auto.commit.interval.ms", "1000");
        //会话超时时间，超过这个时间
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        this.comsumer = new KafkaConsumer<String, String>(props);
        List<String> topics = Arrays.asList("DATA_SYNC");
        ConsumerProcess process = new ConsumerProcess(comsumer, topics);
        process.start();
    }

    class ConsumerProcess extends Thread{

        private KafkaConsumer<String, String> consumer;
        private List<String> topics;

        public ConsumerProcess(KafkaConsumer<String, String> consumer, List<String> topics){
            this.consumer = consumer;
            this.topics = topics;
        }

        @Override
        public void run(){
            System.out.println("{ConsumerProcess} start .");
            consumer.subscribe(topics);
            while(true){
                try{
                    ConsumerRecords<String, String> redcords = consumer.poll(1000);
                    for(ConsumerRecord<String, String> record : redcords){
                        System.out.println("{ConsumerProcess} start process msg :"+record.value());
                        processMsgData(record.topic(), record.value());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        private void processMsgData(String topic, String msg) {
            JSONObject jsonObject = JSONObject.parseObject(msg);
            System.out.println(jsonObject);
//            for(int i = 0; i < jsonArray.size(); i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                System.out.println(jsonObject);
//                //////
//            }
        }
    }
}
