package com.primatec.customerservice.service.implementation;


import com.primatec.customerservice.dto.CustomerDTO;
import com.primatec.customerservice.entity.Customer;
import com.primatec.customerservice.exception.CinAlreadyExistException;
import com.primatec.customerservice.exception.CustomerNotFoundException;
import com.primatec.customerservice.exception.EmailAlreadyExistException;
import com.primatec.customerservice.exception.PhoneAlreadyExistException;
import com.primatec.customerservice.kafka.Event;
import com.primatec.customerservice.kafka.EventProducer;
import com.primatec.customerservice.kafka.EventProducerDelete;
import com.primatec.customerservice.repository.CustomerRepository;
import com.primatec.customerservice.service.CustomerService;
import com.primatec.customerservice.util.Mappers;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {


    private static final String ALREADY_EXIST = "' already exist.";
    private static final String CUSTOMER_WITH_EMAIL = "A customer with email :'";
    private static final String CUSTOMER_WITH_PHONE = "A customer with phone :'";
    private static final String CUSTOMER_WITH_CIN = "A customer with cin :'";

    private final CustomerRepository customerRepository;
    private final Mappers mappers;

    public CustomerServiceImpl(CustomerRepository customerRepository, Mappers mappers) {
        this.customerRepository = customerRepository;
        this.mappers = mappers;
    }

    @Transactional
    @Override
    public CustomerDTO save(CustomerDTO customerDTO) throws CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException {
        log.info("In save() :");
        checkIfCinOrEmailOrPhoneExistBeforeSave(customerDTO);
        Customer customer = mappers.fromCustomerDTO(customerDTO);
        customer.setLastUpdate(null);
        //customer.setId(UUID.randomUUID().toString());
        customer.setCreation(LocalDateTime.now());
        Customer customerSaved = customerRepository.save(customer);
        log.info("customer saved successfully");
        Event customerCreatedEvent=new Event();
        customerCreatedEvent.setCutomerId(customerDTO.id());
        customerCreatedEvent.setName("creation customer");
        EventProducer.sendCustomerCreatedEvent(customerCreatedEvent);
       return mappers.fromCustomer(customerSaved);




    }


    @Transactional
    @Override
    public CustomerDTO update(String id, CustomerDTO customerDTO) throws CustomerNotFoundException, CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException {
        log.info("In update() :");
        Customer customer = customerRepository.findById(id)
                .orElseThrow( ()-> new CustomerNotFoundException("customer you try to update with id = '"+id+"' not found."));
        checkIfCinOrEmailOrPhoneExistBeforeUpdate(customer, customerDTO);
        updateCustomerFields(customer, customerDTO);
        Customer customerUpdate = customerRepository.save(customer);
        log.info("Customer updated successfully");
        return mappers.fromCustomer(customerUpdate);
    }

    @Override
    public CustomerDTO getById(String id) throws CustomerNotFoundException {
        log.info("In getById() :");
        Customer customer = customerRepository.findById(id)
                .orElseThrow( ()-> new CustomerNotFoundException("customer with id = '"+id+"' not found."));
        log.info("customer with id = '"+id+"' found successfully.");
        return mappers.fromCustomer(customer);
    }



    @Override
    public void deleteById(String id) throws CustomerNotFoundException {
        log.info("In deleteById() :");
        Customer customer = customerRepository.findById(id)
                .orElseThrow( ()-> new CustomerNotFoundException("customer with id = '"+id+"' not found."));
        CustomerDTO customerDTO=mappers.fromCustomer(customer);
        Event customerDeletedEvent=new Event();
        customerDeletedEvent.setName("CustomerDeleted");
        customerDeletedEvent.setCutomerId(customerDTO.id());

        customerRepository.deleteById(id);
        EventProducerDelete.sendCustomerDeletedEvent(customerDeletedEvent);
        log.info("customer deleted");
    }

    private void updateCustomerFields(@NotNull Customer customer, @NotNull CustomerDTO customerDTO){
        customer.setFirstname(customerDTO.firstname());
        customer.setName(customerDTO.name());
        customer.setSex(customerDTO.sex());
        customer.setPlaceOfBirth(customerDTO.placeOfBirth());
        customer.setNationality(customerDTO.nationality());
        customer.setCin(customerDTO.cin());
        customer.setEmail(customerDTO.email());
        customer.setPhone(customerDTO.phone());
        customer.setLastUpdate(LocalDateTime.now());
    }


    private void checkIfCinOrEmailOrPhoneExistBeforeSave(@NotNull CustomerDTO customerDTO) throws CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException {
        checkIfCinExists(customerDTO.cin());
        checkIfEmailExists(customerDTO.email());
        checkIfPhoneExists(customerDTO.phone());
    }

    private void checkIfCinOrEmailOrPhoneExistBeforeUpdate(@NotNull Customer customer, @NotNull CustomerDTO customerDTO) throws CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException {
        if (!customer.getCin().equals(customerDTO.cin())){
            checkIfCinExists(customerDTO.cin());
        }
        if(!customer.getEmail().equals(customerDTO.email())){
            checkIfEmailExists(customerDTO.email());
        }
        if(!customer.getPhone().equals(customerDTO.phone())){
            checkIfPhoneExists(customerDTO.phone());
        }
    }



    private void checkIfCinExists(String cin) throws CinAlreadyExistException {
        if (Boolean.TRUE.equals(customerRepository.checkIfCinExists(cin))) {
            throw new CinAlreadyExistException(CUSTOMER_WITH_CIN + cin + ALREADY_EXIST);
        }
    }


    private void checkIfEmailExists(String email) throws EmailAlreadyExistException {
        if (Boolean.TRUE.equals(customerRepository.checkIfEmailExists(email))) {
            throw new EmailAlreadyExistException(CUSTOMER_WITH_EMAIL + email + ALREADY_EXIST);
        }
    }

    private void checkIfPhoneExists(String phone) throws PhoneAlreadyExistException {
        if (Boolean.TRUE.equals(customerRepository.checkIfPhoneExists(phone))) {
            throw new PhoneAlreadyExistException(CUSTOMER_WITH_PHONE + phone + ALREADY_EXIST);
        }
    }
}
