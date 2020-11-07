package com.jzt.conf;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 功能描述：es相关配置类
 *
 * @Author: sj
 * @Date: 2020/11/5 21:59
 */
@Configuration
@PropertySource(value = {"classpath:elasticsearch.properties"})
public class ElasticSearchConfig {

    @Value(value = "${elasticsearch.host}")
    private String host;

    @Value(value = "${elasticsearch.port}")
    private String port;

    @Bean
    public RestHighLevelClient getRestHighLeveClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
        return client;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

}
