package com.example.bankmanagementsystem.Service;


import com.example.bankmanagementsystem.Model.MyUser;
import com.example.bankmanagementsystem.Repostiroy.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;



    public void adduser(MyUser myuser) {
        String hash = new BCryptPasswordEncoder().encode(myuser.getPassword());
        myuser.setPassword(hash);
        myuser.setRole("CUSTOMER");
        authRepository.save(myuser);
    }




}
