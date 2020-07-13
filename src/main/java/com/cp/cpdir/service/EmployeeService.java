package com.cp.cpdir.service;

import com.cp.cpdir.model.Employee;
import com.cp.cpdir.model.Title;
import com.cp.cpdir.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

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
     * @param employee to be added to the repository
     */
    public boolean saveDeveloper(Employee employee) {
        return (titlesLegalityCheck(employee.getTitles(), "developer") && employee.getDirectManager() != null) &&
                saveEmployee(employee);
    }



    /**
     * Saves a new manager if employee doesnt exist
     * @param employee to be added to the repository
     */
    public boolean saveManager(Employee employee) {
        return titlesLegalityCheck(employee.getTitles(), "manager") && saveEmployee(employee);
    }

    /**
     * Removes an employee(except Gil Shwed). If a manager is removed, all of his
     * employees will become employees of his direct manager.
     * @param employeeId to be removed
     */
    public boolean removeEmployee(Long employeeId) {
        try {
            Employee emp = employeeRepository.findById(employeeId).orElse(null);
            if (emp == null) return false;

            Long directManagerId = emp.getDirectManager();
            Employee directManager = employeeRepository.findById(directManagerId).orElse(null);
            if (directManager == null) return false;

            List<Long> directEmps = emp.getReportingEmployees();
            List<Employee> employeesToSave = new ArrayList<>();
            directManager.getReportingEmployees().remove(employeeId);
            employeesToSave.add(directManager);

            for (Long id : directEmps) {
                Employee dirEmp = employeeRepository.findById(id).orElse(null);
                dirEmp.setDirectManager(directManagerId);
                directManager.getReportingEmployees().add(id);
                employeesToSave.add(dirEmp);
            }
            employeeRepository.deleteById(employeeId);
            employeeRepository.saveAll(employeesToSave);

            return true;
        }
        catch (Exception e){
            //Logger
            return false;
        }
    }

    /**
     * Edit employee details
     * @param employeeId of an employee to be edited
     * @param employeeDetails - values of the details to be edited
     */
    public boolean editEmployee(Long employeeId, Employee employeeDetails) {
        try{
            // Do input check before starting to save changes?
            Employee emp = employeeRepository.findById(employeeId).orElse(null);
            if (emp == null || !checkEditValuesValidity(employeeDetails, emp)) return false;
            List<Employee> employeesToSave = new ArrayList<>(); //saves all the employees affected by edit
            employeesToSave.add(emp);
            if (employeeDetails.getId() != null) {
                Employee oldManager = updateDirectManagerIdChange(emp, employeeDetails.getId());
                if (oldManager == null) return false;
                else employeesToSave.add(oldManager);
                emp.setId(employeeDetails.getId());
            }
            if (employeeDetails.getFirstName() != null) emp.setFirstName(employeeDetails.getFirstName());
            if (employeeDetails.getLastName() != null) emp.setLastName(employeeDetails.getLastName());
            if (employeeDetails.getPhone() != null) emp.setPhone(employeeDetails.getPhone());
            if (employeeDetails.getTeamName() != null) emp.setTeamName(employeeDetails.getTeamName());
            if (employeeDetails.getTitles() != null ) emp.setTitles(employeeDetails.getTitles());
            if (employeeDetails.getDirectManager() != null){
                Employee oldManager = updateDirectManagerIdChange(emp, employeeDetails.getId());
                if (oldManager == null) return false;
                else employeesToSave.add(oldManager);

                emp.setDirectManager(employeeDetails.getDirectManager());
                Employee newManager = addDirectManagerReportingEmp(emp, employeeDetails.getDirectManager());
                if (newManager == null) return false;
                else employeesToSave.add(newManager);
            }
            employeeRepository.saveAll(employeesToSave);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * finds all employees reporting to a specific employee
     * @param employeeId id of an employee that his employees are required to be returned
     * @param order true for ascending, false for descending
     * @return All employees reporting to Employee with id @employeeId
     */
    public Map<String, List<Employee>> reportingEmployees(Long employeeId, Boolean order) {
        Employee emp = employeeRepository.findById(employeeId).orElse(null);
        if (emp == null) return Collections.singletonMap("failed", new LinkedList<>());
        return order ?
                Collections.singletonMap("employees",
                        employeeRepository.findByIdInOrderByLastNameAscFirstNameAsc(gatherReportingEmployees(emp))) :
                Collections.singletonMap("employees",
                        employeeRepository.findByIdInOrderByLastNameDescFirstNameDesc(gatherReportingEmployees(emp)));
    }

    // =================================================================================================

    /**
     * Updates employee's manager's directEmployees because of id change
     * @param employee whos id is about to change
     * @param newId of the employee
     * @return old manager with updated id
     */
    private Employee updateDirectManagerIdChange(Employee employee, Long newId){
        try{
            Employee oldManager = employeeRepository.findById(employee.getDirectManager()).orElse(null);
            if (oldManager == null) return null;
            oldManager.getReportingEmployees().remove(employee.getId());
            oldManager.getReportingEmployees().add(newId);
            return oldManager;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Adds employee to newManager's reporting employees
     * @param employee employee to be added
     * @param newManagerId that will receive @employee as a direct employee
     * @return Manager after change in case of success, null otherwise
     */
    private Employee addDirectManagerReportingEmp(Employee employee, Long newManagerId){
        try {
            Employee newManager = employeeRepository.findById(newManagerId).orElse(null);
            if (newManager == null) return null;
            newManager.getReportingEmployees().add(employee.getId());
            return newManager;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * saves an employee
     * @param employee to save in repository
     * @return true iff succeeded to save employee
     */
    private boolean saveEmployee(Employee employee){
        try {
            List<Employee> employeesToSave = new ArrayList<>();
            employee.setReportingEmployees(new LinkedList<>());
            if (employee.getId() != 1) { // saved for Gil Shwed
                Employee dirManager = employeeRepository.findById(employee.getDirectManager()).orElse(null);
                if (dirManager == null || !titlesLegalityCheck(dirManager.getTitles(), "manager")) return false;
                dirManager.getReportingEmployees().add(employee.getId());
                employeesToSave.add(dirManager);
            }
            employeesToSave.add(employee);
            employeeRepository.saveAll(employeesToSave);

            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Returns all employees records
     * @return List of employees in DB
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Checks if input str is legal - only numbers and longer then 0.
     * @param str to be checked
     * @return true iff id is legal
     */
    private boolean numericLegalityCheck(String str){
        return str != null && str.matches("[0-9]+") && str.length() > 0;
    }

    /**
     * Checks if input id is legal - only numbers and longer then 0.
     * @param titles to check
     * @param type can be developer | manager
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
     * @param employeeDetails new values of the details to be edited
     * @param currentEmp Employee instance of the Employee to be edited
     * @return true iff all the non vals of @employeeDetails are legal
     */
    private boolean checkEditValuesValidity(Employee employeeDetails, Employee currentEmp){
        if (employeeDetails.getId() != null && ((employeeDetails.getId() == 1) || employeeRepository.existsById(employeeDetails.getId()))) return false;
        if (employeeDetails.getFirstName() != null && employeeDetails.getFirstName().length() == 0) return false;
        if (employeeDetails.getLastName() != null && employeeDetails.getLastName().length() == 0) return false;
        if (employeeDetails.getTeamName() != null && employeeDetails.getTeamName().length() == 0) return false;
        if (employeeDetails.getTitles() != null &&
                ((currentEmp.getReportingEmployees().size() != 0 && !titlesLegalityCheck(employeeDetails.getTitles(), "manager")))) return false;
        if (employeeDetails.getDirectManager() != null && (!employeeRepository.existsById(employeeDetails.getDirectManager()) || containsManagementCircularity(employeeDetails, currentEmp))) return false;
        return employeeDetails.getPhone() == null || numericLegalityCheck(employeeDetails.getPhone());
    }

    /**
     * Checks if there will be a management circle after changing DirectManager of currentEmp to newEmp's.
     * Check by going up by management ladder and trying to reach root(CEO) from newEmp without meeting currentEmp
     * @param currentEmp represents an employee that is going to be edited
     * @param employeeDetails represents edited employee new values
     * @return true iff there will be a management circularity after changing direct management of @currentEmp
     */
    private boolean containsManagementCircularity(Employee employeeDetails, Employee currentEmp){
        Employee dirManager = employeeRepository.findById(employeeDetails.getDirectManager()).orElse(null);
        while(dirManager != null){
            if (dirManager.getId() == 1) // Reached Root - Gil Shwed
            {
                return false;
            }
            else if (dirManager.getId().equals(currentEmp.getId())) // Reached currentEmp - circularity case
            {
                return true;
            }
            dirManager = employeeRepository.findById(dirManager.getDirectManager()).orElse(null);
        }
        return true; // A case that doesnt contain circle but also if did happen - something went wrong
    }

    private List<Long> gatherReportingEmployees(Employee emp){
        List<Long> reportingEmps = new LinkedList<>();
        for(Long id: emp.getReportingEmployees()){
            Employee repEmp = employeeRepository.findById(id).orElse(null);
            if (repEmp == null) return null;
            reportingEmps.add(repEmp.getId());
            List<Long> reportingEmployeesIds = gatherReportingEmployees(repEmp);
            if (reportingEmployeesIds == null) return null;
            reportingEmps.addAll(reportingEmployeesIds);
        }
        return reportingEmps;
    }

}
