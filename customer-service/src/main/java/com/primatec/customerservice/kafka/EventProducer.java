package com.primatec.customerservice.kafka;



import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventProducer {
    private static final Logger LOGGER= LoggerFactory.getLogger(EventProducer.class);
    private static NewTopic topic1;
    private static KafkaTemplate<String, Event> kafkaTemplate;


    public EventProducer(NewTopic topic1, KafkaTemplate<String, Event> kafkaTemplate) {
        EventProducer.topic1 = topic1;
        this.kafkaTemplate = kafkaTemplate;
    }

    public static void sendCustomerCreatedEvent(Event customerCreated ){
        LOGGER.info(String.format("Creating customer event => %s", customerCreated.toString()));
        log.info("event : {}",customerCreated );
        Message<Event> message= MessageBuilder
                .withPayload(customerCreated)
                .setHeader(KafkaHeaders.TOPIC, topic1.name())
                .build();
        kafkaTemplate.send(message);
    }

}
