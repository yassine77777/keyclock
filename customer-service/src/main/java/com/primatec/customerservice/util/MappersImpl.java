package com.primatec.customerservice.util;

import com.primatec.customerservice.dto.CustomerDTO;
import com.primatec.customerservice.entity.Customer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MappersImpl implements Mappers {

    @Override
    public CustomerDTO fromCustomer(@NotNull Customer customer){
        return new CustomerDTO(
                customer.getId(), customer.getFirstname(), customer.getName(),
                customer.getPlaceOfBirth(), customer.getDateOfBirth(),
                customer.getNationality(), customer.getSex(), customer.getCin(),
                customer.getEmail(), customer.getPhone(), customer.getCreation(),
                customer.getLastUpdate()
        );
    }




    @Override
    public Customer fromCustomerDTO(@NotNull CustomerDTO customerDTO) {
        return Customer.builder()
                .id(customerDTO.id()).firstname(customerDTO.firstname())
                .name(customerDTO.name()).placeOfBirth(customerDTO.placeOfBirth())
                .dateOfBirth(customerDTO.dateOfBirth()).nationality(customerDTO.nationality())
                .sex(customerDTO.sex()).cin(customerDTO.cin()).email(customerDTO.email())
                .phone(customerDTO.phone()).creation(customerDTO.creation())
                .lastUpdate(customerDTO.lastUpdate())
                .build();
    }




    @Override
    public List<CustomerDTO> fromListOfCustomers(@NotNull List<Customer> customers){
        return customers.stream().map(this::fromCustomer).toList();
    }

    public MappersImpl(){
        super();
    }
}
