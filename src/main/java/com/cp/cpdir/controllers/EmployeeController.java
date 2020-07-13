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
        String ans;
        return (ans = employeeService.saveDeveloper(employee)) == null ?
                Collections.singletonMap("employees", employeeService.getAllEmployees()) :
                Collections.singletonMap(ans, null);
    }

    @PostMapping("/addManager")
    public Map<String, List<Employee>> addNewManager(@RequestBody Employee employee){
        String ans;
        return (ans = employeeService.saveManager(employee)) == null ?
                Collections.singletonMap("employees", employeeService.getAllEmployees()) :
                Collections.singletonMap(ans, null);
    }

    @DeleteMapping("/removeEmployee/{id}")
    public Map<String, List<Employee>> removeEmployee(@PathVariable(value = "id") Long userId){
        String ans;
        return (ans = employeeService.removeEmployee(userId)) == null ?
                Collections.singletonMap("employees", employeeService.getAllEmployees()) :
                Collections.singletonMap(ans, null);
    }

    @PostMapping("/editEmployee/{id}")
    public Map<String, List<Employee>> editEmployee(@PathVariable(value = "id") Long userId, @RequestBody Employee employeeDetails){
        String ans;
        return (ans = employeeService.editEmployee(userId, employeeDetails)) == null ?
                Collections.singletonMap("employees", employeeService.getAllEmployees()) :
                Collections.singletonMap(ans, null);
    }

    @GetMapping("/reportingEmployees/{id},{order}")
    public Map<String, List<Employee>> reportingEmployees(@PathVariable(value = "id") Long userId, @PathVariable(value = "order") Boolean order){
        return employeeService.reportingEmployees(userId, order);

    }

}
