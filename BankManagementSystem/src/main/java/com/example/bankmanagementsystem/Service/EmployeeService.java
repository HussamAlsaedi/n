package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.ApiResponse.ApiException;
import com.example.bankmanagementsystem.DTOs_IN.EmployeeDTO;
import com.example.bankmanagementsystem.Model.Employee;
import com.example.bankmanagementsystem.Model.MyUser;
import com.example.bankmanagementsystem.Repostiroy.AuthRepository;
import com.example.bankmanagementsystem.Repostiroy.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public List<Employee> getAllEmployees(Integer userId) {
        MyUser  myUser = authRepository.findMyUserById(userId);
        if(myUser == null) {
            throw new ApiException("not found");
        }
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer userId, Integer employeeId ) {
        MyUser  myUser = authRepository.findMyUserById(userId);
        if(myUser == null) {
            throw new ApiException("not found");
        }
        Employee employee =  employeeRepository.findEmployeeById(employeeId);
        if (employee == null) {
            throw new ApiException("Employee not found");
        }

        return employee;
    }


    public void register(EmployeeDTO employeeDTO) {

        MyUser myUser = new MyUser();
        myUser.setName(employeeDTO.getName());
        myUser.setUsername(employeeDTO.getUsername());
        myUser.setPassword(passwordEncoder.encode(employeeDTO.getPassword()) );
        myUser.setEmail(employeeDTO.getEmail());
        myUser.setRole("EMPLOYEE");

        Employee employee = new Employee();
        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());
        employee.setMyuser(myUser);

        authRepository.save(myUser);
        employeeRepository.save(employee);
    }


    public void updateEmployee(MyUser myUser, EmployeeDTO employeeDTO) {
        if (!myUser.getRole().equals("ADMIN") && !myUser.getRole().equals("EMPLOYEE")
                && !myUser.getEmployee().getId().equals(employeeDTO.getEmployeeId())) {
            throw new ApiException("You do not have permission to update this employee.");
        }

        Employee oldEmployee = employeeRepository.findEmployeeById(employeeDTO.getEmployeeId());
        if (oldEmployee == null) {
            throw new ApiException("Employee not found");
        }

        MyUser oldUser = authRepository.findMyUserById(oldEmployee.getMyuser().getId());

        oldUser.setName(employeeDTO.getName());
        oldUser.setUsername(employeeDTO.getUsername());
        oldUser.setPassword(passwordEncoder.encode(employeeDTO.getPassword()) );
        oldUser.setEmail(employeeDTO.getEmail());
        oldUser.setRole("EMPLOYEE");

        oldEmployee.setPosition(employeeDTO.getPosition());
        oldEmployee.setSalary(employeeDTO.getSalary());
        oldEmployee.setMyuser(oldUser);

        employeeRepository.save(oldEmployee);
        authRepository.save(oldUser);

    }

  public void deleteEmployee(MyUser myUser) {
      if (!myUser.getRole().equals("ADMIN") && !myUser.getRole().equals("EMPLOYEE")) {
          throw new ApiException("You do not have permission to update this employee.");
      }

      Employee emp = employeeRepository.findEmployeeById(myUser.getId());
      if (emp == null) {
          throw new ApiException("Employee not found");
      }
      MyUser user = authRepository.findMyUserById(emp.getMyuser().getId());
      emp.setMyuser(null);
     
      employeeRepository.delete(emp);
    }

}
