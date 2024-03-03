package com.primatec.customerservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Value(value = "${spring.kafka.topic.name}")
    private String topicName1;
    @Value(value = "${spring.kafka.topic.name.other}")
    private String topicName2;

    //spring bean for kafka topic
@Bean
    public NewTopic topic1(){
        return TopicBuilder.name(topicName1).build();
    }
    @Bean
    public NewTopic topic2(){
        return TopicBuilder.name(topicName2).build();
    }

}



