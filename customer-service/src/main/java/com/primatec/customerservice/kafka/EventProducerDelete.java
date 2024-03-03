package com.primatec.customerservice.kafka;



import com.primatec.customerservice.dto.CustomerDTO;
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
public class EventProducerDelete {
    private static final Logger LOGGER= LoggerFactory.getLogger(EventProducerDelete.class);
    private static NewTopic topic2;
    private static KafkaTemplate<String, Event> kafkaTemplate;


    public EventProducerDelete(NewTopic topic2, KafkaTemplate<String, Event> kafkaTemplate) {
        EventProducerDelete.topic2 = topic2;
        this.kafkaTemplate = kafkaTemplate;
    }


    public static void sendCustomerDeletedEvent(Event customerDeletedEvent){
        LOGGER.info(String.format("Deleting customer event => %s", customerDeletedEvent.toString()));
        log.info("event : {}",customerDeletedEvent);
        Message<Event> message= MessageBuilder
                .withPayload(customerDeletedEvent)
                .setHeader(KafkaHeaders.TOPIC, topic2.name())
                .build();
        kafkaTemplate.send(message);
    }
}
