package com.example.bankmanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Customer {

    @Id

    private Integer id;

    @NotEmpty(message = "phone Number is mandatory")
    @Pattern(regexp = "^05[0-9]{8}$", message = "Phone number must start with '05' and be 10 digits long.")
    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser myuser;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Account> account;
}
