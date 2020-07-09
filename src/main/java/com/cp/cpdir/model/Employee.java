package com.cp.cpdir.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
@EntityListeners(AuditingEntityListener.class)
public class Employee {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "titles", nullable = false)
    @ElementCollection(targetClass = Title.class)
    private List<Title> titles;

    @Column(name = "direct_manager", nullable = false)
    private String directManager;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "reporting_employees")
    @ElementCollection(targetClass = Employee.class)
    private List<Employee> reportingEmployees;


    // =========================================== Getters & Setters ============================================
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public String getDirectManager() {
        return directManager;
    }

    public void setDirectManager(String directManager) {
        this.directManager = directManager;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Employee> getReportingEmployees() {
        return reportingEmployees;
    }

    public void setReportingEmployees(List<Employee> reportingEmployees) {
        this.reportingEmployees = reportingEmployees;
    }


}
