package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.ApiResponse.ApiResponse;
import com.example.bankmanagementsystem.DTOs_IN.DTOsOUT.AccountDTOsOUT;
import com.example.bankmanagementsystem.Model.Account;
import com.example.bankmanagementsystem.Model.MyUser;
import com.example.bankmanagementsystem.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/getAllAccounts")
    public List<Account> getAllAccounts(@AuthenticationPrincipal MyUser myUser) {

       return accountService.getAllAccounts(myUser.getId());
    }

    @GetMapping("/getCustomerAccount")
    public void getCustomerAccount(@AuthenticationPrincipal MyUser myUser) {
        accountService.getCustomerAccount(myUser.getId());
    }

    @PostMapping("/createAccount")
    public ResponseEntity<ApiResponse> createAccount(@AuthenticationPrincipal MyUser myUser, @RequestBody Account account) {
        accountService.createAccount(myUser.getId(), account);
        return ResponseEntity.ok(new ApiResponse("Account created successfully"));
    }

    @PutMapping("/updateAccount")
    public ResponseEntity<ApiResponse> updateAccount(@AuthenticationPrincipal MyUser myUser, @RequestBody Account account) {
        accountService.updateAccount(myUser.getId(), account);
        return ResponseEntity.ok(new ApiResponse("Account updated successfully"));
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<ApiResponse> deleteAccount(@AuthenticationPrincipal MyUser myUser) {
        accountService.deleteAccount(myUser.getId());
       return ResponseEntity.ok(new ApiResponse("Account deleted successfully"));
    }

    @GetMapping("/ActiveAccount/{accountId}")
    public ResponseEntity<ApiResponse> ActiveAccount(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer accountId) {
        accountService.ActiveAccount(myUser.getId(), accountId);
        return ResponseEntity.ok(new ApiResponse("Account activated successfully"));
    }

    @GetMapping("/BlockAccount/{accountId}")
    public ResponseEntity<ApiResponse> BlockAccount(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer accountId) {
        accountService.BlockAccount(myUser.getId(), accountId);
        return ResponseEntity.ok(new ApiResponse("Account Blocked successfully"));
    }

    @GetMapping("/deposit/{amount}")
    public ResponseEntity<ApiResponse> Deposit(@AuthenticationPrincipal MyUser myUser,@PathVariable Double amount) {
        accountService.Deposit(myUser.getId(), amount);
        return ResponseEntity.ok(new ApiResponse("Deposit successfully"));
    }

    @GetMapping("/Withdraw/{amount}")
    public ResponseEntity<ApiResponse> Withdraw(@AuthenticationPrincipal MyUser myUser,@PathVariable Double amount) {
        accountService.Withdraw(myUser.getId(), amount);
        return ResponseEntity.ok(new ApiResponse("Withdraw successfully"));
    }
    @GetMapping("/transferFunds/{receiverAccountId}/{amount}")
    public ResponseEntity<ApiResponse> transferFunds(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer receiverAccountId,@PathVariable Double amount) {
        accountService.transferFunds(myUser.getId(), receiverAccountId, amount);
        return ResponseEntity.ok(new ApiResponse("successfully transfer "));
    }


}
