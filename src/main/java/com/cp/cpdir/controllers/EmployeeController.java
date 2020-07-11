package com.cp.cpdir.controllers;

import com.cp.cpdir.model.Employee;
import com.cp.cpdir.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * A REST controller that exposes REST endpoints to APIs functionality
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    interface Resp { }

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/addDeveloper")
    public Map<String, List<Employee>> addNewDeveloper(@RequestBody Employee employee){

        return employeeService.saveDeveloper(employee) ?
                Collections.singletonMap("employees", employeeService.getAllEmployees()) :
                Collections.singletonMap("failed", null);
    }

    @PostMapping("/addManager")
    public Map<String, List<Employee>> addNewManager(@RequestBody Employee employee){
        return employeeService.saveManager(employee) ?
                Collections.singletonMap("employees", employeeService.getAllEmployees()) :
                Collections.singletonMap("failed", null);
    }

    @DeleteMapping("/removeEmployee/{id}")
    public Map<String, List<Employee>> removeEmployee(@PathVariable(value = "id") Long userId){
        return employeeService.removeEmployee(userId) ?
                Collections.singletonMap("employees", employeeService.getAllEmployees()) :
                Collections.singletonMap("failed", null);
    }

    @PutMapping("/editEmployee/{id}")
    public Map<String, List<Employee>> editEmployee(@PathVariable(value = "id") Long userId, @RequestBody Employee employeeDetails){
        return employeeService.editEmployee(userId, employeeDetails) ?
                Collections.singletonMap("employees", employeeService.getAllEmployees()) :
                Collections.singletonMap("failed", null);
    }

    @GetMapping("/reportingEmployees/{id},{order}")
    public Map<String, List<Employee>> reportingEmployees(@PathVariable(value = "id") Long userId, @PathVariable(value = "order") Boolean order){
        return employeeService.reportingEmployees(userId, order);

    }

}
