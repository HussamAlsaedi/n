package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.ApiResponse.ApiResponse;
import com.example.bankmanagementsystem.DTOs_IN.CustomerDTO;
import com.example.bankmanagementsystem.DTOs_IN.EmployeeDTO;
import com.example.bankmanagementsystem.Model.MyUser;
import com.example.bankmanagementsystem.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor

public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/registerCustomer")
    public ResponseEntity<ApiResponse> registerCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        customerService.register(customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully registered customer"));
    }

    @PutMapping("/update-Customer")
    public ResponseEntity<ApiResponse> updateCustomer(
            @AuthenticationPrincipal MyUser myUser,
            @Valid @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(myUser, customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("customer updated successfully"));
    }


    @DeleteMapping("/delete-customer")
    public ResponseEntity<ApiResponse> deleteCustomer(@AuthenticationPrincipal MyUser myUser) {
        customerService.deleteCustomer(myUser);
        return ResponseEntity.status(200).body(new ApiResponse("Customer deleted successfully"));
    }
}
