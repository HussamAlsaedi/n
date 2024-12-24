package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.ApiResponse.ApiException;
import com.example.bankmanagementsystem.DTOs_IN.DTOsOUT.AccountDTOsOUT;
import com.example.bankmanagementsystem.Model.Account;
import com.example.bankmanagementsystem.Model.MyUser;
import com.example.bankmanagementsystem.Repostiroy.AccountRepository;
import com.example.bankmanagementsystem.Repostiroy.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AuthRepository authRepository;

    public List<Account> getAllAccounts(Integer id) {
        MyUser user =  authRepository.findMyUserById(id);
        if(!user.getRole().equals("ADMIN")&&!user.getRole().equals("EMPLOYEE")) {
            throw new ApiException("You do not have permission.");
        }
        return accountRepository.findAll();
    }

    public void getCustomerAccount(Integer id) {

        MyUser user = authRepository.findMyUserById(id);
        if (user == null) {
            throw new ApiException("User not found.");
        }

        if (!"CUSTOMER".equals(user.getRole())) {
            throw new ApiException("You do not have permission.");
        }

        Account account = accountRepository.findAccountsById(user.getId());
        if (account == null) {
            throw new ApiException("No account found for the given customer.");
        }

        if (account.isBlock()){
            throw new ApiException("Account is not blocked.");
        }

        Account accounts =  accountRepository.findAccountsById(user.getId());

        AccountDTOsOUT acc = new AccountDTOsOUT();
        acc.setActive(accounts.isActive());
        acc.setBalance(accounts.getBalance());
        acc.setId(accounts.getId());
        acc.setAccountNumber(accounts.getAccountNumber());
        acc.print();
    }

    public void createAccount(Integer id, Account account) {
        MyUser user = authRepository.findMyUserById(id);
        if(!user.getRole().equals("CUSTOMER")) {
            throw new ApiException("You do not have permission.");
        }

        account.setCustomer(user.getCustomer());
        account.setActive(false);
        accountRepository.save(account);
    }

    public void updateAccount(Integer id, Account account) {
        MyUser user = authRepository.findMyUserById(id);
        if(!user.getRole().equals("CUSTOMER") )  {
            throw new ApiException("You do not have permission.");
        }

        Account oldAccount = accountRepository.findAccountsById(account.getId());
        if (oldAccount == null) {
            throw new ApiException("Account does not exist.");
        }

        if (oldAccount.isBlock()){
            throw new ApiException("Account is not blocked.");
        }

        oldAccount.setAccountNumber(account.getAccountNumber());
        oldAccount.setBalance(account.getBalance());
        oldAccount.setCustomer(account.getCustomer());
        accountRepository.save(oldAccount);

    }

    public void deleteAccount(Integer id) {
        MyUser user = authRepository.findMyUserById(id);
        if(!user.getRole().equals("CUSTOMER") )  {
            throw new ApiException("You do not have permission.");
        }

        Account Account = accountRepository.findAccountsById(id);
        if (Account == null) {
            throw new ApiException("Account does not exist.");
        }
        if (Account.isBlock()){
            throw new ApiException("Account is not blocked.");
        }

        accountRepository.delete(Account);
    }

    public void ActiveAccount(Integer userId, Integer accountId) {
        MyUser user = authRepository.findMyUserById(userId);
        if(!user.getRole().equals("ADMIN") && !user.getRole().equals("EMPLOYEE")) {
            throw new ApiException("You do not have permission.");
        }
        Account account = accountRepository.findAccountsById(accountId);
        if (account == null) {
            throw new ApiException("Account does not exist.");
        }
        account.setActive(true);
        accountRepository.save(account);
    }

    public void BlockAccount(Integer userId, Integer accountId) {
        MyUser user = authRepository.findMyUserById(userId);
        if(!user.getRole().equals("ADMIN") && !user.getRole().equals("EMPLOYEE")) {
            throw new ApiException("You do not have permission.");
        }
        Account account = accountRepository.findAccountsById(accountId);
        if (account == null) {
            throw new ApiException("Account does not exist.");
        }
        account.setBlock(true);
        accountRepository.save(account);
    }

    public void Deposit(Integer userId, Double amount) {

        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) {
            throw new ApiException("User not found.");
        }

        if (!"CUSTOMER".equals(user.getRole())) {
            throw new ApiException("You do not have permission.");
        }

        Account account = accountRepository.findAccountsById(user.getId());
        if (account == null) {
            throw new ApiException("No account found for the given customer.");
        }
        if (account.isBlock()){
            throw new ApiException("Account is not blocked.");
        }
        if (amount == null || amount <= 0) {
            throw new ApiException("Deposit amount must be greater than 0.");
        }
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

    }

    public void Withdraw(Integer userId, Double amount) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) {
            throw new ApiException("User not found.");
        }

        if (!"CUSTOMER".equals(user.getRole())) {
            throw new ApiException("You do not have permission.");
        }

        Account account = accountRepository.findAccountsById(user.getId());
        if (account == null) {
            throw new ApiException("No account found for the given customer.");
        }
        if (account.isBlock()){
            throw new ApiException("Account is not blocked.");
        }
        if (amount == null || amount <= 0) {
            throw new ApiException("Withdrawal amount must be greater than 0.");
        }

        if (account.getBalance() < amount) {
            throw new ApiException("Insufficient balance.");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    public void transferFunds(Integer userId, Integer receiverAccountId, Double amount) {

        MyUser sender = authRepository.findMyUserById(userId);
        if (sender == null) {
            throw new ApiException("Sender not found.");
        }

        if (!"CUSTOMER".equals(sender.getRole())) {
            throw new ApiException("You do not have permission to perform a transfer.");
        }

        Account senderAccount = accountRepository.findAccountsById(sender.getId());
        if (senderAccount == null) {
            throw new ApiException("Sender account not found.");
        }
        if (senderAccount.isBlock()){
            throw new ApiException("senderAccount is not blocked.");
        }

        Account receiverAccount = accountRepository.findAccountsById(receiverAccountId);
        if (receiverAccount == null) {
            throw new ApiException("Receiver account not found.");
        }

        if (receiverAccount.isBlock()){
            throw new ApiException("receiverAccount is not blocked.");
        }

        if (amount == null || amount <= 0) {
            throw new ApiException("Transfer amount must be greater than 0.");
        }

        if (senderAccount.getBalance() < amount) {
            throw new ApiException("Insufficient balance in sender's account.");
        }
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);
    }



}
