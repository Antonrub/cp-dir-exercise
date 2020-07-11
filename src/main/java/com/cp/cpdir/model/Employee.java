package com.cp.cpdir.model;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
@EntityListeners(AuditingEntityListener.class)
public class Employee {

    @Id
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "titles", nullable = false)
    @ElementCollection(targetClass = Title.class)
    @CollectionTable(name = "titles")
    private List<Title> titles;

    @Column(name = "direct_manager", nullable = false)
    private Long directManager;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "reporting_employees")
    @ElementCollection(targetClass = Long.class)
    private List<Long> reportingEmployees;


    // =========================================== Getters & Setters ============================================
    public Long getId() { return id; }

    public void setId(Long id) {
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

    public List<Title> getTitles() { return titles; }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public Long getDirectManager() {
        return directManager;
    }

    public void setDirectManager(Long directManager) {
        this.directManager = directManager;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Long> getReportingEmployees() {
        return reportingEmployees;
    }

    public void setReportingEmployees(List<Long> reportingEmployees) {
        this.reportingEmployees = reportingEmployees;
    }


}
