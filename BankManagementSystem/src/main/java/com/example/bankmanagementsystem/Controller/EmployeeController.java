package com.example.bankmanagementsystem.Controller;


import com.example.bankmanagementsystem.ApiResponse.ApiResponse;
import com.example.bankmanagementsystem.DTOs_IN.EmployeeDTO;
import com.example.bankmanagementsystem.Model.Employee;
import com.example.bankmanagementsystem.Model.MyUser;
import com.example.bankmanagementsystem.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/getAllEmployees")
    public List<Employee> getAllEmployees(@AuthenticationPrincipal MyUser myUser) {
        return employeeService.getAllEmployees(myUser.getId());
    }
    @GetMapping("/getEmployeeById/{employeeId}")
    public Employee getEmployeeById(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer employeeId) {
        return employeeService.getEmployeeById(myUser.getId(),employeeId);
    }

    @PostMapping("/registerEmployee")
    public ResponseEntity<ApiResponse> registerEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        employeeService.register(employeeDTO);
       return ResponseEntity.status(200).body(new ApiResponse("Successfully registered employee"));
    }

    @PutMapping("/update-employee")
    public ResponseEntity<ApiResponse> updateEmployee(
            @AuthenticationPrincipal MyUser myUser,
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(myUser, employeeDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Employee updated successfully"));
    }

    @DeleteMapping("/delete-employee")
    public ResponseEntity<ApiResponse> deleteEmployee(@AuthenticationPrincipal MyUser myUser) {
        employeeService.deleteEmployee(myUser);
        return ResponseEntity.status(200).body(new ApiResponse("Employee deleted successfully"));
    }



}
