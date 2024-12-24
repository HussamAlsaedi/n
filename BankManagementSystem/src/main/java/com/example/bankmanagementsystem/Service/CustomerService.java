package com.example.bankmanagementsystem.Service;


import com.example.bankmanagementsystem.ApiResponse.ApiException;
import com.example.bankmanagementsystem.DTOs_IN.CustomerDTO;
import com.example.bankmanagementsystem.DTOs_IN.EmployeeDTO;
import com.example.bankmanagementsystem.Model.Customer;
import com.example.bankmanagementsystem.Model.Employee;
import com.example.bankmanagementsystem.Model.MyUser;
import com.example.bankmanagementsystem.Repostiroy.AuthRepository;
import com.example.bankmanagementsystem.Repostiroy.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<Customer> findAll(Integer userId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) {
            throw new ApiException("user not found");
        }
        return customerRepository.findAll();
    }

    public void register(CustomerDTO customerDTO) {

        MyUser myUser = new MyUser();
        myUser.setName(customerDTO.getName());
        myUser.setUsername(customerDTO.getUsername());
        myUser.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        myUser.setEmail(customerDTO.getEmail());
        myUser.setRole("CUSTOMER");

        Customer customer = new Customer();
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        customer.setMyuser(myUser);

        authRepository.save(myUser);
        customerRepository.save(customer);
    }

    public void updateCustomer(MyUser myUser, CustomerDTO customerDTO) {
        if (!myUser.getRole().equals("ADMIN") && !myUser.getRole().equals("EMPLOYEE") && !myUser.getRole().equals("CUSTOMER")
                && !myUser.getEmployee().getId().equals(customerDTO.getCustomerId())) {
            throw new ApiException("You do not have permission to update this employee.");
        }

        Customer oldCustomer = customerRepository.findCustomerById(customerDTO.getCustomerId());
        if (oldCustomer == null) {
            throw new ApiException("Customer not found");
        }

        MyUser oldUser = authRepository.findMyUserById(oldCustomer.getMyuser().getId());

        oldUser.setName(customerDTO.getName());
        oldUser.setUsername(customerDTO.getUsername());
        oldUser.setPassword(passwordEncoder.encode(customerDTO.getPassword()) );
        oldUser.setEmail(customerDTO.getEmail());
        oldUser.setRole("CUSTOMER");

        oldCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
        oldCustomer.setMyuser(oldUser);

        customerRepository.save(oldCustomer);
        authRepository.save(oldUser);

    }


    public void deleteCustomer(MyUser myUser) {
        if (!"ADMIN".equals(myUser.getRole()) && !"EMPLOYEE".equals(myUser.getRole()) && !myUser.getRole().equals("CUSTOMER")) {
            throw new ApiException("You do not have permission to delete this employee.");
        }

        Customer customer = customerRepository.findCustomerById(myUser.getId());
        if (customer == null) {
            throw new ApiException("Employee not found");
        }
        MyUser user = authRepository.findMyUserById(customer.getMyuser().getId());
        System.out.printf("Delete customer %s\n", customer.getMyuser().getId());
        customerRepository.delete(customer);

    }

}