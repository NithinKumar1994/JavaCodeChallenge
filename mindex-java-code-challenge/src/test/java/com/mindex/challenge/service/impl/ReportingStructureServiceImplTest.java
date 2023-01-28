package com.mindex.challenge.service.impl;


import com.mindex.challenge.DataBootstrap;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private static final String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
    private static final Integer numOfReports = 4;
    @Autowired
    DataBootstrap dataBootstrap;
    private String employeeUrl;
    private String employeeIdUrl;
    private String reportingStructureUrl;
    @Autowired
    private EmployeeService employeeService;
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Employee employee;

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

    @Before
    public void setup() {

        reportingStructureUrl = "http://localhost:" + port + "/reporting/{id}";
        employee = Employee.builder()
                .employeeId("16a596ae-edd3-4847-99fe-c4518e82c86f")
                .firstName("John")
                .lastName("Lennon")
                .position("Development Manager")
                .department("Engineering").directReports(new ArrayList<Employee>()).build();


    }

    @Test
    public void testRead() {

        ReportingStructure reportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, employeeId).getBody();

        assertNotNull(reportingStructure);
        assertEmployeeEquivalence(reportingStructure.getEmployee(), employee);
        assertEquals(reportingStructure.getNumberOfReports(), numOfReports);

    }

    @Test
    public void testRead_IncorrectEmployeeId() {

        ReportingStructure reportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, "Test").getBody();
        assertNull(reportingStructure.getEmployee());

    }

    @Test
    public void testRead_EmptyEmployeeId() {

        ReportingStructure reportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, "").getBody();
        assertNull(reportingStructure.getEmployee());

    }
}
