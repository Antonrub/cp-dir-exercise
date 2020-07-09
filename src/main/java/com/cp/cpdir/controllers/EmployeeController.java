package com.cp.cpdir.controllers;

import com.cp.cpdir.model.Employee;
import com.cp.cpdir.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
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
        return employeeService.saveDeveloper(employee) ? employeeService.getAllEmployees() : new LinkedList<>();
    }

    @PostMapping("/addManager")
    public List<Employee> addNewManager(@RequestBody Employee employee){
        return employeeService.saveManager(employee) ? employeeService.getAllEmployees() : new LinkedList<>();
    }

    @DeleteMapping("/removeEmployee/{id}")
    public List<Employee> removeEmployee(@PathVariable(value = "id") String userId){
        return employeeService.removeEmployee(userId) ? employeeService.getAllEmployees() : new LinkedList<>();
    }

    @PutMapping("/editEmployee/{id}")
    public List<Employee> editEmployee(@PathVariable(value = "id") String userId, @RequestBody Employee employeeDetails){
        return employeeService.editEmployee(userId, employeeDetails) ? employeeService.getAllEmployees() : new LinkedList<>();
    }

    @GetMapping("/reportingEmployees/{id},{order}")
    public List<Employee> reportingEmployees(@PathVariable(value = "id") String userId, @PathVariable(value = "order") Boolean order){
        return employeeService.reportingEmployees(userId, order);

    }

}
