package com.example.bankmanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Check(constraints = "role in ('CUSTOMER','EMPLOYEE', 'ADMIN')")
public class MyUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 2, max = 20, message = "Length of name must be between 2 and 20 characters.")
    @NotEmpty(message = "name is mandatory")
    @Column(nullable = false, length = 20)
    private String name;

    @NotEmpty(message = "username is mandatory")
    @Size(min = 4, max = 10, message = "Length of username must be between 4 and 10 characters.")
    @Column(unique = true, nullable = false)
    private String username;

    @NotEmpty(message = "password is mandatory")
  //  @Size(min = 6, message = "Length of Length must be at least 6 characters.")
    @Column(nullable = false)
    private String password;

    @Email(message = "Email must be valid")
    @NotEmpty(message = "email is mandatory")
    @Column(unique = true, nullable = false)
    private String email;


    @Pattern(regexp = "CUSTOMER|EMPLOYEE|ADMIN", message = "role must be one of these options CUSTOMER or EMPLOYEE or ADMIN")
    @NotEmpty(message = "role is mandatory")
    @Column(nullable = false)
    private String role;


    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Employee employee;


    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
     private Customer customer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
