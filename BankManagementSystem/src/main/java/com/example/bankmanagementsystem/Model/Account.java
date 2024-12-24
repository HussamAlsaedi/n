package com.example.bankmanagementsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "accountNumber is mandatory")
    @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}", message = "Account number must follow the format XXXX-XXXX-XXXX-XXXX")
    private String accountNumber;

    @NotNull(message = "accountNumber is mandatory")
    private Double balance;

    private boolean isActive =false;

    private boolean block =false;

    @ManyToOne
    private Customer customer;


}
