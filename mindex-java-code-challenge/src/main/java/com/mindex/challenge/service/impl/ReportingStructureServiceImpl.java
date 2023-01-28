package com.mindex.challenge.service.impl;


import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class used to read number of Reports under an employee.
 */
@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureService.class);

    @Autowired
    private EmployeeServiceImpl employeeService;

    /**
     * Get Number Of Reports
     *
     * @param employeeId
     * @return
     */
    @Override
    public ReportingStructure getNumberOfReports(String employeeId) {

        LOG.debug("Fetching number of Reports");


        Integer numOfReports = numOfReportsHelper(employeeId, 0);

        ReportingStructure reportingStructure = ReportingStructure.builder()
                .employee(employeeService.read(employeeId))
                .numberOfReports(numOfReports).build();

        return reportingStructure;

    }

    /**
     * Method used to calculate number of reports under a employee. The method uses recursion to count the number.
     *
     * @param employeeId   Employee Id of the employee whose number of reportees are calculated.
     * @param numOfReports Number of Reports
     * @return number Of Reports
     */
    public int numOfReportsHelper(String employeeId, int numOfReports) {

        Employee employee = employeeService.read(employeeId);

        if (employee.getDirectReports() != null) {
            for (Employee e : employee.getDirectReports()) {
                numOfReports = numOfReportsHelper(e.getEmployeeId(), numOfReports + 1);
            }
        }

        return numOfReports;
    }
}
