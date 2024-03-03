package com.primatec.accountservice.kafka;

import com.primatec.accountservice.entity.Account;
import com.primatec.accountservice.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class EventConsumerDeleted {
    @Autowired
    AccountRepository accountRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventConsumerCreated.class);




    @KafkaListener(
            topics = "${spring.kafka.topic.name.other}"
            , groupId ="${spring.kafka.consumer.group-id}"
    )
    public void consumeDeletedCustomerEvent(Event customerDeletedEvent) throws AccountNotFoundException {

            LOGGER.info(String.format("Delete event of a customer is recieved in account service => %s", customerDeletedEvent.toString()));
            Account account = accountRepository.findByCustomerId(customerDeletedEvent.getCutomerId());
            if (account == null) {
                throw new AccountNotFoundException("Account not found with customerId '" + customerDeletedEvent + "'.");
            }
            accountRepository.delete(account);


    }



}
