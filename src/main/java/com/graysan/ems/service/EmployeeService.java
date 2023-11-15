package com.graysan.ems.service;

import com.graysan.ems.exception.GraysanException;
import com.graysan.ems.exception.ResourceNotFoundException;
import com.graysan.ems.model.Employee;
import com.graysan.ems.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id){
        Optional<Employee> emp;
        if (id != null){
            emp = employeeRepository.findById(id);
            return emp.orElseThrow(
                    () -> new ResourceNotFoundException("Employee not found with id: " + id));
        }else {
            throw new GraysanException("Id is null!");
        }
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }



    public String deleteEmployee(Long id){
        Employee emp = getEmployeeById(id);
        if(emp == null){
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }else{
            employeeRepository.deleteById(id);
        }

        return "Deleted: " + emp.getFirstName() + " " + emp.getLastName();
    }
}
