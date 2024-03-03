package com.primatec.customerservice.util;

import com.primatec.customerservice.dto.CustomerDTO;
import com.primatec.customerservice.entity.Customer;
import org.jetbrains.annotations.NotNull;


import java.util.List;


public interface Mappers {


    CustomerDTO fromCustomer(Customer customer);

    Customer fromCustomerDTO(CustomerDTO customerDTO);

    List<CustomerDTO> fromListOfCustomers(@NotNull List<Customer> customers);


}
