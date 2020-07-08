package com.cp.cpdir.service;


import com.cp.cpdir.model.Employee;
import com.cp.cpdir.repository.EmployeeRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that will facilitate operations by communicating with the repository
 */

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    /**
     * Saves a new developer if doesnt already exist
     * @param employee
     */
    public boolean saveDeveloper(Employee employee) {
        // Make sure that id consists of numbers only
        if (employee.getId() != null && employee.getId().matches("[0-9]+") && employee.getId().length() > 0) {
            try {
                employeeRepository.save(employee);
                return true;
            }
            catch (ConstraintViolationException e){
                //Logger?
                return false;
            }
        }
        return true;
    }

    /**
     * Saves a new manager if employee doesnt exist
     * @param employee
     */
    public void saveManager(Employee employee) {

    }

    /**
     * Returns all employees records
     * @return List of employees in DB
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Removes an employee(except Gil Shwed). If a manager is removed, all of his
     * employees will become employees of his direct manager.
     * @param employee to be removed
     */
    public void removeEmployee(String employee) {
        return;
    }

    /**
     * Edit employee details
     * @param userId
     * @param employeeDetails
     */
    public void editEmployee(String userId, Employee employeeDetails) {
        return;
    }

    /**
     * finds all employees reporting to a specific employee
     * @param userId
     * @param order true for ascending, false for descending
     * @return
     */
    public List<Employee> reportingEmployees(String userId, Boolean order) {
        return null;
    }


}
