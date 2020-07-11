package com.cp.cpdir;

import com.cp.cpdir.model.Employee;
import com.cp.cpdir.model.Title;
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

import java.util.Arrays;
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
    public void addNewDeveloperTest() {

        Employee employee = new Employee();

        employee.setId(2L);
        employee.setFirstName("A1");
        employee.setLastName("A1");
        employee.setDirectManager(1L);
        employee.setPhone("1");
        employee.setTeamName("First Team");
        employee.setTitles(new LinkedList<>(Arrays.asList(Title.SoftwareDeveloper)));



        ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl() + "/addDeveloper", employee, Employee.class);

        Assert.assertNotNull(postResponse);

        Assert.assertNotNull(postResponse.getBody());

    }

    @Test
    public void addNewManagerTest() {

        Employee employee = new Employee();

        employee.setId(1L);
        employee.setFirstName("Gil");
        employee.setLastName("Shwed");
        employee.setDirectManager(1L);
        employee.setPhone("02133254");
        employee.setTeamName("CP");
        employee.setTitles(new LinkedList<>(Arrays.asList(Title.CEO)));



        ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl() + "/addManager", employee, Employee.class);

        Assert.assertNotNull(postResponse);

        Assert.assertNotNull(postResponse.getBody());

    }

}
