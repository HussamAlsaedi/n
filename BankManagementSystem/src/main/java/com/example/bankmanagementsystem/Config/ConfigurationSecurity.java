package com.example.bankmanagementsystem.Config;


import com.example.bankmanagementsystem.Service.MyUserDeletesService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigurationSecurity {

    private final MyUserDeletesService myUserDeletesService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDeletesService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET,"api/v1/employee/getAllEmployees","api/v1/employee/getEmployeeById/").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/user/register","api/v1/employee/registerEmployee").permitAll()
                .requestMatchers(HttpMethod.PUT,"api/v1/employee/update-employee").hasAnyRole("EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.DELETE,"api/v1/employee/update-employee").hasAnyRole("EMPLOYEE","ADMIN")

                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/api/v1/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
