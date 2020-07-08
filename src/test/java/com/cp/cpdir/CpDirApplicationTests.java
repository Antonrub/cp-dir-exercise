package com.cp.cpdir;

import com.cp.cpdir.model.Employee;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpDirApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CpDirApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port = 3306;

    private String getRootUrl(){
        return "http://localhost:" + port + "/api/v1";
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testGetAllEmployees() {

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/users",

                HttpMethod.GET, entity, String.class);

        Assert.assertNotNull(response.getBody());

    }

    @Test
    public void testCreateUser() {

        Employee employee = new Employee();

        employee.setId("1");
        employee.setFirstName("a");
        employee.setLastName("a");
        employee.setDirectManager("12345");
        employee.setPhone(02133254);
        employee.setTeamName("TOC");
        employee.setTitles(new LinkedList<>());



        ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl() + "/addDeveloper", employee, Employee.class);

        Assert.assertNotNull(postResponse);

        Assert.assertNotNull(postResponse.getBody());

    }

}
