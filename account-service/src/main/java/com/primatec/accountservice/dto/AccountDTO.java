package com.primatec.accountservice.dto;

import com.primatec.accountservice.enums.AccountStatus;
import com.primatec.accountservice.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountDTO(String id,
                         String customerId,
                         BigDecimal balance,
                         Currency currency,
                         AccountStatus status,
                         LocalDateTime creation,
                         LocalDateTime lastUpdate,
                         CustomerDTO customer) {
}
