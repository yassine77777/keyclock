package com.primatec.accountservice.kafka;



import com.primatec.accountservice.entity.Account;
import com.primatec.accountservice.enums.AccountStatus;
import com.primatec.accountservice.enums.Currency;
import com.primatec.accountservice.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EventConsumerCreated {
    @Autowired AccountRepository accountRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventConsumerCreated.class);


    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            , groupId ="${spring.kafka.consumer.group-id}"
    )
    public void consumeCreatedCustomerEvent(Event customerCreatedEvent) {

            LOGGER.info(String.format("Creation event of a customer is recieved in account service => %s", customerCreatedEvent.toString()));
            Account account = new Account();
            account.setId(UUID.randomUUID().toString());
            account.setCustomerId(customerCreatedEvent.getCutomerId());
            account.setCurrency(Currency.EUR);
            account.setCreation(LocalDateTime.now());
            account.setBalance(BigDecimal.ONE);
            account.setStatus(AccountStatus.ACTIVATED);
            accountRepository.save(account);



    }




}
