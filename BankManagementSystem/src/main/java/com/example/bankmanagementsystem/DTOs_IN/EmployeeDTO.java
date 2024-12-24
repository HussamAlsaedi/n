package com.example.bankmanagementsystem.DTOs_IN;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {


    private Integer employeeId ;

    @Size(min = 2, max = 20, message = "Length of name must be between 2 and 20 characters.")
    @NotEmpty(message = "name is mandatory")
    private String name;

    @NotEmpty(message = "username is mandatory")
    @Size(min = 4, max = 10, message = "Length of username must be between 4 and 10 characters.")
    private String username;

    @NotEmpty(message = "password is mandatory")
    private String password;

    @Email(message = "Email must be valid")
    @NotEmpty(message = "email is mandatory")
    private String email;


    @Column(nullable = false)
    private String role;

    @NotEmpty(message = "position is mandatory")
    @Column(nullable = false)
    private  String position;

    @NotNull(message = "salary is mandatory")
    @Positive(message = "Must be a non-negative decimal number.")
    private Double salary;
}
