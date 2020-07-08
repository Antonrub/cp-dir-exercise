package com.cp.cpdir.controllers;

import com.cp.cpdir.model.Employee;
import com.cp.cpdir.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A REST controller that exposes REST endpoints to APIs functionality
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/addDeveloper")
    public List<Employee> addNewDeveloper(@RequestBody Employee employee){
        employeeService.saveDeveloper(employee);
        return null;
    }

    @PostMapping("/addManager")
    public List<Employee> addNewManager(@RequestBody Employee employee){
        employeeService.saveManager(employee);
        return null;
    }

    @DeleteMapping("/removeEmployee/{id}")
    public List<Employee> removeEmployee(@PathVariable(value = "id") String userId){
        employeeService.removeEmployee(userId);
        return employeeService.getAllEmployees();
    }

    @PutMapping("/editEmployee/{id}")
    public List<Employee> editEmployee(@PathVariable(value = "id") String userId, @RequestBody Employee employeeDetails){
        employeeService.editEmployee(userId, employeeDetails);
        return employeeService.getAllEmployees();
    }

    @GetMapping("/reportingEmployees/{id},{order}")
    public List<Employee> reportingEmployees(@PathVariable(value = "id") String userId, @PathVariable(value = "order") Boolean order){
        return employeeService.reportingEmployees(userId, order);

    }

}
