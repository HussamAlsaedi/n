package com.example.bankmanagementsystem.Repostiroy;

import com.example.bankmanagementsystem.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findAccountsById(Integer id);
}
