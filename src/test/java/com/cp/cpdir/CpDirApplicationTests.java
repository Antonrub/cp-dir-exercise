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

import java.util.*;

/**
 * These tests were made for me to semi-auto check my code    * Doesn't work as intended JUnit
 */
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

//    @Test
//    void contextLoads() {
//        Employee employee1 = new Employee();
//        Employee employee2 = new Employee();
//        Employee employee3 = new Employee();
//        Employee employee4 = new Employee();
//        Employee employee5 = new Employee();
//        Employee employee6 = new Employee();
//        Employee employee7 = new Employee();
//        Employee employee8 = new Employee();
//        Employee employee9 = new Employee();
//        Employee employee10 = new Employee();
//
//        employee1.setId(1L);
//        employee1.setFirstName("Gil");
//        employee1.setLastName("Shwed");
//        employee1.setDirectManager(1L);
//        employee1.setPhone("02133254");
//        employee1.setTeamName("CP");
//        employee1.setTitles(new LinkedList<>(Arrays.asList(Title.CEO)));
//
//        employee2.setId(2L);
//        employee2.setFirstName("Cheche");
//        employee2.setLastName("Mongo");
//        employee2.setDirectManager(1L);
//        employee2.setPhone("1");
//        employee2.setTeamName("First Team");
//        employee2.setTitles(new LinkedList<>(Arrays.asList(Title.TeamLeader, Title.SoftwareDeveloper)));
//
//        employee3.setId(3L);
//        employee3.setFirstName("Zoe");
//        employee3.setLastName("Zill");
//        employee3.setDirectManager(1L);
//        employee3.setPhone("1");
//        employee3.setTeamName("Second Team");
//        employee3.setTitles(new LinkedList<>(Arrays.asList(Title.TeamLeader, Title.SoftwareDeveloper)));
//
//        employee4.setId(4L);
//        employee4.setFirstName("Babpo");
//        employee4.setLastName("Choco");
//        employee4.setDirectManager(1L);
//        employee4.setPhone("1");
//        employee4.setTeamName("Third Team");
//        employee4.setTitles(new LinkedList<>(Arrays.asList(Title.TeamLeader, Title.TechnologyLeader)));
//
//        employee5.setId(5L);
//        employee5.setFirstName("B");
//        employee5.setLastName("A");
//        employee5.setDirectManager(2L);
//        employee5.setPhone("1");
//        employee5.setTeamName("First Team");
//        employee5.setTitles(new LinkedList<>(Arrays.asList(Title.SoftwareDeveloper, Title.TechnologyLeader)));
//
//        employee6.setId(6L);
//        employee6.setFirstName("A");
//        employee6.setLastName("C");
//        employee6.setDirectManager(2L);
//        employee6.setPhone("1");
//        employee6.setTeamName("First Team");
//        employee6.setTitles(new LinkedList<>(Arrays.asList(Title.SoftwareDeveloper)));
//
//        employee7.setId(7L);
//        employee7.setFirstName("X");
//        employee7.setLastName("X");
//        employee7.setDirectManager(3L);
//        employee7.setPhone("1");
//        employee7.setTeamName("Second Team");
//        employee7.setTitles(new LinkedList<>(Arrays.asList(Title.SoftwareDeveloper)));
//
//        employee8.setId(8L);
//        employee8.setFirstName("Charlie");
//        employee8.setLastName("C");
//        employee8.setDirectManager(4L);
//        employee8.setPhone("1");
//        employee8.setTeamName("Third Team");
//        employee8.setTitles(new LinkedList<>(Arrays.asList(Title.TechnologyLeader)));
//
//        employee9.setId(9L);
//        employee9.setFirstName("Babun");
//        employee9.setLastName("Choco");
//        employee9.setDirectManager(4L);
//        employee9.setPhone("1");
//        employee9.setTeamName("Third Team");
//        employee9.setTitles(new LinkedList<>(Arrays.asList(Title.TeamLeader)));
//
//        employee10.setId(10L);
//        employee10.setFirstName("Babon");
//        employee10.setLastName("Choco");
//        employee10.setDirectManager(4L);
//        employee10.setPhone("1");
//        employee10.setTeamName("Third Team");
//        employee10.setTitles(new LinkedList<>(Arrays.asList(Title.SoftwareDeveloper)));
//
//
//        ResponseEntity<Map> postResponse1 = restTemplate.postForEntity(getRootUrl() + "/addManager", employee1, Map.class);
//        ResponseEntity<Map> postResponse2 = restTemplate.postForEntity(getRootUrl() + "/addManager", employee2, Map.class);
//        ResponseEntity<Map> postResponse3 = restTemplate.postForEntity(getRootUrl() + "/addManager", employee3, Map.class);
//        ResponseEntity<Map> postResponse4 = restTemplate.postForEntity(getRootUrl() + "/addManager", employee4, Map.class);
//        ResponseEntity<Map> postResponse5 = restTemplate.postForEntity(getRootUrl() + "/addManager", employee5, Map.class);
//        ResponseEntity<Map> postResponse6 = restTemplate.postForEntity(getRootUrl() + "/addDeveloper", employee6,Map.class);
//        ResponseEntity<Map> postResponse7 = restTemplate.postForEntity(getRootUrl() + "/addDeveloper", employee7,Map.class);
//        ResponseEntity<Map> postResponse8 = restTemplate.postForEntity(getRootUrl() + "/addManager", employee8, Map.class);
//        ResponseEntity<Map> postResponse9 = restTemplate.postForEntity(getRootUrl() + "/addManager", employee9, Map.class);
//        ResponseEntity<Map> postResponse10 = restTemplate.postForEntity(getRootUrl() + "/addDeveloper", employee10, Map.class);
//    }
//
//
//    @Test
//    public void addNewDeveloperTest() {
//
//        Employee employee = new Employee();
//
//        employee.setId(2L);
//        employee.setFirstName("Cheche");
//        employee.setLastName("Mongo");
//        employee.setDirectManager(1L);
//        employee.setPhone("1");
//        employee.setTeamName("First Team");
//        employee.setTitles(new LinkedList<>(Arrays.asList(Title.TechnologyLeader)));
//
//
//        ResponseEntity<Map> postResponse = restTemplate.postForEntity(getRootUrl() + "/addDeveloper", employee, Map.class);
//        Assert.assertNotNull(postResponse);
//        Assert.assertNotNull(postResponse.getBody());
//
//    }
//
//    @Test
//    public void addNewManagerTest() {
//
//        Employee employee = new Employee();
//
//        employee.setId(2L);
//        employee.setFirstName("Cheche");
//        employee.setLastName("Mongo");
//        employee.setDirectManager(8L);
//        employee.setPhone("02133254");
//        employee.setTeamName("Third Team");
//        employee.setTitles(new LinkedList<>(Arrays.asList(Title.CEO)));
//
//        ResponseEntity<Map> postResponse = restTemplate.postForEntity(getRootUrl() + "/addManager", employee, Map.class);
//
//        Assert.assertNotNull(postResponse);
//
//        Assert.assertNotNull(postResponse.getBody());
//
//    }
//
//    @Test
//    public void reportingEmployeesTest(){
//        ResponseEntity<Map> getResponse = restTemplate.getForEntity(getRootUrl() + "/reportingEmployees/1,true", Map.class);
//
//        Assert.assertNotNull(getResponse);
//
//        Assert.assertNotNull(getResponse.getBody());
//    }
//
//    @Test
//    public void editEmployeeTest(){
//        Employee employee3 = new Employee();
//
//        employee3.setLastName("Babu");
//        employee3.setDirectManager(9L);
//        employee3.setPhone("2");
//        employee3.setTeamName("Third Team");
//        employee3.setTitles(new LinkedList<>(Arrays.asList(Title.TechnologyLeader, Title.SoftwareDeveloper)));
//
//        ResponseEntity<Map> postResponse = restTemplate.postForEntity(getRootUrl() + "/editEmployee/3", employee3, Map.class);
//
//        Assert.assertNotNull(postResponse);
//
//        Assert.assertNotNull(postResponse.getBody());
//    }
//
//    @Test
//    public void removeEmployeeTest(){
//        restTemplate.delete(getRootUrl() + "/removeEmployee/2");
//
//        Assert.assertNotNull(new Object());
//    }

}
