package com.graysan.ems.controller;

import com.graysan.ems.exception.ResourceNotFoundException;
import com.graysan.ems.model.Employee;
import com.graysan.ems.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Employee>> getAll(){
        try{
            List<Employee> temp = employeeService.getAllEmployees();
            return ResponseEntity.ok(temp);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id){
        try{
            Employee temp = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(temp);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveEmployee(@RequestBody Employee employee){
        try{
            Employee result = employeeService.saveEmployee(employee);
            if (result != null){
                return ResponseEntity.ok("Saved successfully");
            }else {
                return ResponseEntity.internalServerError().body("Could not save successfully");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Could not save successfully");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        try {
            String resultMessage = employeeService.deleteEmployee(id);
            return ResponseEntity.ok(resultMessage);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete successfully");
        }
    }
}
