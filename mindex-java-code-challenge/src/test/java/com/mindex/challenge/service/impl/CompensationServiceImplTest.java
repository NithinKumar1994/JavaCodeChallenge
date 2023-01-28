package com.mindex.challenge.service.impl;

import com.mindex.challenge.controller.CompensationController;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {


    private String compensationUrl;
    private String compensationIdUrl;


    @Mock
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private CompensationController compensationController;

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
        assertEquals(expected.getEmployee().getFirstName(), actual.getEmployee().getFirstName());
        assertEquals(expected.getEmployee().getLastName(), actual.getEmployee().getLastName());
        assertEquals(expected.getEmployee().getDepartment(), actual.getEmployee().getDepartment());
        assertEquals(expected.getEmployee().getDirectReports(), actual.getEmployee().getDirectReports());
        assertEquals(expected.getEmployee().getPosition(), actual.getEmployee().getPosition());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @After
    public void teardown() {

    }

    @Test
    public void testCreateRead() throws Exception {
        Employee employee = Employee.builder().employeeId("b7839309-3348-463b-a7e3-5de1c168beb3").firstName("Paul").lastName("McCartney").position("Developer I").department("Engineering").build();

        when(employeeService.read("b7839309-3348-463b-a7e3-5de1c168beb3")).thenReturn(employee);
        when(compensationService.create(anyObject())).thenReturn(Compensation.builder().employee(employee).salary(10000.00).effectiveDate(Date.valueOf("2023-01-01")).build());
        employee = employeeService.read("b7839309-3348-463b-a7e3-5de1c168beb3");
        Compensation testCompensation = new Compensation();


        testCompensation.setEmployee(employee);
        testCompensation.setSalary(10000.00);
        testCompensation.setEffectiveDate(Date.valueOf("2023-01-01"));

        // Create compensation
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
        assertNotNull(createdCompensation.getEmployee());
        assertCompensationEquivalence(testCompensation, createdCompensation);

        // Read compensation
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, createdCompensation.getEmployee().getEmployeeId()).getBody();
        assertEquals(createdCompensation.getSalary(), readCompensation.getSalary());
        assertCompensationEquivalence(createdCompensation, readCompensation);

    }
}
