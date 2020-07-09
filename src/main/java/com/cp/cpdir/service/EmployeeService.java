package com.cp.cpdir.service;


import com.cp.cpdir.model.Employee;
import com.cp.cpdir.model.Title;
import com.cp.cpdir.repository.EmployeeRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service class that will facilitate operations by communicating with the repository
 */

@Service
public class EmployeeService {

    private List<Title> _managerialTitles = Arrays.asList(Title.CEO, Title.TeamLeader, Title.TechnologyLeader);

    @Autowired
    EmployeeRepository employeeRepository;


    // ========================================== API functionality ===========================================
    /**
     * Saves a new developer if doesnt already exist
     * @param employee
     */
    public boolean saveDeveloper(Employee employee) {
        // Make sure that id consists of numbers only
        if (numericLegalityCheck(employee.getId()) && titlesLegalityCheck(employee.getTitles(), "developer")) {
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
    public boolean saveManager(Employee employee) {
        if (numericLegalityCheck(employee.getId()) && titlesLegalityCheck(employee.getTitles(), "manager")) {
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
     * Removes an employee(except Gil Shwed). If a manager is removed, all of his
     * employees will become employees of his direct manager.
     * @param employeeId to be removed
     */
    public boolean removeEmployee(String employeeId) {
        try {
            Employee emp = employeeRepository.findById(employeeId).orElse(null);
            if (emp == null) return false;

            String directManagerId = emp.getDirectManager();
            Employee directManager = employeeRepository.findById(directManagerId).orElse(null);
            if (directManager == null) return false;

            List<Employee> directEmps = emp.getReportingEmployees();

            employeeRepository.deleteById(employeeId);

            for (Employee e : directEmps) {
                e.setDirectManager(directManagerId);
                directManager.getReportingEmployees().add(e);
                employeeRepository.save(e);
            }

            employeeRepository.save(directManager);

            return true;
        }
        catch (ConstraintViolationException e){
            //Logger?
            return false;
        }
    }

    /**
     * Edit employee details
     * @param employeeId
     * @param employeeDetails
     */
    public boolean editEmployee(String employeeId, Employee employeeDetails) {
        try{
            // Do input check before starting to save changes?
            Employee emp = employeeRepository.findById(employeeId).orElse(null);
            if (emp == null) return false;

            if (employeeDetails.getFirstName() != null) emp.setFirstName(employeeDetails.getFirstName());
            if (employeeDetails.getLastName() != null) emp.setLastName(employeeDetails.getLastName());
            if (employeeDetails.getPhone() != null) emp.setPhone(employeeDetails.getPhone());
            if (employeeDetails.getTeamName() != null) emp.setTeamName(employeeDetails.getTeamName());
            if (employeeDetails.getTitles() != null &&
                    (titlesLegalityCheck(employeeDetails.getTitles(), "manager") || emp.getReportingEmployees().isEmpty()))
                emp.setTitles(employeeDetails.getTitles());                // Similar to input test?

            //TODO: edit ID and direct manager.
        }
        catch (ConstraintViolationException e){

        }
        return false;
    }

    /**
     * finds all employees reporting to a specific employee
     * @param employeeId
     * @param order true for ascending, false for descending
     * @return
     */
    public List<Employee> reportingEmployees(String employeeId, Boolean order) {
        return null;
    }

    // =================================================================================================

    /**
     * Returns all employees records
     * @return List of employees in DB
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Checks if input id is legal - only numbers and longer then 0.
     * @param id
     * @return true iff id is legal
     */
    private boolean numericLegalityCheck(String id){
        return id != null && id.matches("[0-9]+") && id.length() > 0;
    }

    /**
     * Checks if input id is legal - only numbers and longer then 0.
     * @param titles
     * @param type
     * @return true iff titles correspond to type
     */
    private boolean titlesLegalityCheck(List<Title> titles, String type) {
        switch (type){
            case "developer":
                return titles.contains(Title.SoftwareDeveloper);
            case "manager":
                for (Title t : titles) {
                    if (_managerialTitles.contains(t)) return true;
                }
                return false;
        }
        return false;
    }

    /**
     * Check all the input values for validity
     * @param employeeDetails
     * @param currentEmp
     * @return
     */
    private boolean checkEditValuesValidity(Employee employeeDetails, Employee currentEmp){
        if (employeeDetails.getId() != null && !numericLegalityCheck(employeeDetails.getId())) return false;
        if (employeeDetails.getFirstName() != null && employeeDetails.getFirstName().length() == 0) return false;
        if (employeeDetails.getLastName() != null && employeeDetails.getLastName().length() == 0) return false;
        if (employeeDetails.getTeamName() != null && employeeDetails.getTeamName().length() == 0) return false;
        if (employeeDetails.getTitles() != null && currentEmp.getReportingEmployees().size() != 0 && !titlesLegalityCheck(employeeDetails.getTitles(), "manager")) return false;
        if (employeeDetails.getDirectManager() != null && (!employeeRepository.existsById(employeeDetails.getDirectManager()) || containsManagementCircularity(employeeDetails, currentEmp))) return false;
        if (employeeDetails.getPhone() != null && !numericLegalityCheck(employeeDetails.getPhone())) return false;
        return true;
    }

    /**
     * Checks if there will be a management circle after changing DirectManager or currentEmp to newEmp's.
     * Check by going up by management ladder and trying to reach root(CEO) from newEmp without meeting currentEmp
     * @param currentEmp represents an employee that is going to be edited
     * @param employeeDetails represents edited employee new values
     * @return
     */
    private boolean containsManagementCircularity(Employee employeeDetails, Employee currentEmp){
        return false;
    }

}
