package com.cp.cpdir.repository;

import com.cp.cpdir.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByIdOrderByLastNameAscFirstNameAsc(List<Long> ids);
    List<Employee> findAllByIdOrderByLastNameDescFirstNameDesc(List<Long> ids);
}
