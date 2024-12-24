package com.example.bankmanagementsystem.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.Manager;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Employee {
    @Id
    private Integer id ;

    @NotEmpty(message = "position is mandatory")
    @Column(nullable = false)
    private  String position;

    @NotNull(message = "salary is mandatory")
    @Positive(message = "Must be a non-negative decimal number.")
    @Column(nullable = false)
    private Double salary;

    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser myuser;



}
