package com.example.bankmanagementsystem.DTOs_IN.DTOsOUT;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDTOsOUT {

    private Integer id;
    private String accountNumber;
    private Double balance;
    private boolean isActive;

    public AccountDTOsOUT() {

    }


    public void print() {
        System.out.printf("Account ID: %s\n", id);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
        System.out.println("Active Status: " + isActive);
    }
}
