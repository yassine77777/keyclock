package com.primatec.accountservice.util.implementation;

import com.primatec.accountservice.dto.AccountDTO;
import com.primatec.accountservice.entity.Account;
import com.primatec.accountservice.util.Mappers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class MappersImpl implements Mappers {

    @Override
    public Account fromAccountDTO(@NotNull AccountDTO dto) {
        return Account.builder().id(dto.id()).customerId(dto.customerId())
                .balance(dto.balance()).currency(dto.currency())
                .status(dto.status()).lastUpdate(dto.lastUpdate())
                .creation(dto.creation()).build();
    }

    @Override
    public AccountDTO fromAccount(@NotNull Account account) {
        return new AccountDTO(account.getId(), account.getCustomerId(),
                account.getBalance(), account.getCurrency(),
                account.getStatus(), account.getCreation(),
                account.getLastUpdate(), account.getCustomer()
        );
    }


}