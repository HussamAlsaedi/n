package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.ApiResponse.ApiException;
import com.example.bankmanagementsystem.Model.MyUser;
import com.example.bankmanagementsystem.Repostiroy.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDeletesService implements UserDetailsService {

    private final AuthRepository authRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myuser = authRepository.findByUsername(username);
        if(myuser == null){
            throw new ApiException("Wrong username or password");
        }
        return myuser;
    }
}
