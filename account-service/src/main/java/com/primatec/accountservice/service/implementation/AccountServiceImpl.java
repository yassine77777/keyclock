package com.primatec.accountservice.service.implementation;

import com.primatec.accountservice.controller.CustomerRestClient;
import com.primatec.accountservice.entity.Customer;
import com.primatec.accountservice.service.AccountService;

import com.primatec.accountservice.dto.AccountDTO;
import com.primatec.accountservice.dto.CustomerDTO;
import com.primatec.accountservice.entity.Account;
import com.primatec.accountservice.enums.AccountStatus;
import com.primatec.accountservice.exception.AccountNotFoundException;
import com.primatec.accountservice.exception.CustomerAlreadyHaveAccountException;
import com.primatec.accountservice.exception.CustomerNotFoundException;
import com.primatec.accountservice.repository.AccountRepository;


import com.primatec.accountservice.util.IdGenerator;
import com.primatec.accountservice.util.Mappers;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final Mappers mappers;
    private final CustomerRestClient customerRestClient;
    private final IdGenerator idGenerator;

    public AccountServiceImpl(AccountRepository accountRepository, Mappers mappers, CustomerRestClient customerRestClient, IdGenerator idGenerator) {
        this.accountRepository = accountRepository;
        this.mappers = mappers;
        this.customerRestClient = customerRestClient;
        this.idGenerator = idGenerator;
    }

    @Override
    public AccountDTO getById(String id) throws AccountNotFoundException {
        log.info("In getById() :");
        Account account = accountRepository.findById(id)
                .orElseThrow( () -> new AccountNotFoundException("Account not found with id '"+id+"'."));
        log.info("account found with id '"+id+"' found successfully.");
        CustomerDTO customer=customerRestClient.getById(account.getCustomerId());
        log.info("Customer details retrieved successfully: {}", customer);
        account.setCustomer(customer);
        log.info("Account: {}", account);

        return mappers.fromAccount(account);
    }


    @Override
    public AccountDTO getByCustomerId(String customerId) throws AccountNotFoundException {
        log.info("In getByCustomerId() :");
        Account account = accountRepository.findByCustomerId(customerId);
        if(account == null){
            throw new AccountNotFoundException("Account not found with customerId '"+customerId+"'.");
        }
        log.info("account found successfully with customerId '"+customerId+"'.");
        return mappers.fromAccount(account);
    }

    @Transactional
    @Override
    public AccountDTO save(@NotNull AccountDTO accountDTO) throws CustomerNotFoundException, CustomerAlreadyHaveAccountException {
        log.info("In save() :");
        CustomerDTO customer = getCustomerById(accountDTO.customerId());
        if(customer == null){
            throw new CustomerNotFoundException("Customer with id '"+accountDTO.customerId()+"not found");
        }
        if(Boolean.TRUE.equals(accountRepository.checkIfCustomerIdExists(customer.id()))){
            throw new CustomerAlreadyHaveAccountException("this customer already have an account");
        }
        String accountId = idGenerator.autoGenerate();
        Account account = Account.builder() .customerId(customer.id()).id(accountId)

                .balance(BigDecimal.valueOf(0)).status(AccountStatus.ACTIVATED)
                .currency(accountDTO.currency()).lastUpdate(null).creation(LocalDateTime.now())
                .build();
        Account accountSaved = accountRepository.save(account);
        log.info("account created successfully");
        return mappers.fromAccount(accountSaved);
    }




    @Override
    public void deleteById(String id) {
        log.info("In deleteById() :");
        accountRepository.deleteById(id);
        log.info("account deleted successfully");
    }

    @Transactional
    @Override
    public AccountDTO updateStatus(@NotNull AccountDTO accountDTO) throws AccountNotFoundException {
        log.info("In updateStatus() :");
        Account account = accountRepository.findById(accountDTO.id())
                .orElseThrow(() -> new AccountNotFoundException("Account you try to update status not found. id '"+accountDTO.id()+"'."));
        account.setStatus(accountDTO.status());
        account.setLastUpdate(LocalDateTime.now());
        Account accountUpdated = accountRepository.save(account);
        log.info("account updated successfully");
        return mappers.fromAccount(accountUpdated);
    }


    private @Nullable CustomerDTO getCustomerById(String customerId) {
        log.info("In getCustomerById()");
        try{
            CustomerDTO customerDTO = customerRestClient.getById(customerId);
            log.info("customer found:{}",customerDTO);
            return customerDTO;

        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }


}
