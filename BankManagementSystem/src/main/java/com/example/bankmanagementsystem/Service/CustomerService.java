package com.example.bankmanagementsystem.Service;


import com.example.bankmanagementsystem.ApiResponse.ApiException;
import com.example.bankmanagementsystem.DTOs_IN.CustomerDTO;
import com.example.bankmanagementsystem.Model.Customer;
import com.example.bankmanagementsystem.Model.MyUser;
import com.example.bankmanagementsystem.Repostiroy.AuthRepository;
import com.example.bankmanagementsystem.Repostiroy.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public List<Customer> findAll(Integer userId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) {
            throw new ApiException("user not found");
        }
        return customerRepository.findAll();
    }

    public void add(CustomerDTO customerDTO) {

        Customer customer = new Customer();
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        customerRepository.save(customer);
    }
}
