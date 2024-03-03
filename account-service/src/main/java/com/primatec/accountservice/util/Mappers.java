package com.primatec.accountservice.util;

import com.primatec.accountservice.dto.AccountDTO;
import com.primatec.accountservice.entity.Account;


public interface Mappers {

    Account fromAccountDTO(AccountDTO dto);

    AccountDTO fromAccount(Account account);


}
