package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.exceptions.CompensationNotFoundException;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import com.mongodb.MongoWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service classed used to read and write Compensation data and
 */
@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompensationRepository compensationRepository;

    /**
     * Post Method for Compensation. Check if employee exists and then write the compensation.
     *
     * @param compensation compensation class
     * @return compensation object
     */
    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Check if Employee exists");

        employeeService.read(compensation.getEmployee().getEmployeeId());

        LOG.debug("Creating Compensation {}", compensation);

        try {
            compensationRepository.insert(compensation);
        } catch (MongoWriteException e) {
            LOG.error("An error occured when inserting into MongoDB {}", e.getMessage(), e);
        }

        return compensation;
    }

    /**
     * Get method for Compensation.
     *
     * @param employeeId Employee id whose compensation will be fetched
     * @return Compensation object
     */
    @Override
    public Compensation read(String employeeId) {

        Employee employee = employeeService.read(employeeId);

        LOG.debug("Fetching compensation for employee id");
        Compensation compensation = compensationRepository.findByEmployee(employee);

        if (compensation == null) {
            throw new CompensationNotFoundException("Compensation doesn't exist");
        }

        return compensation;
    }


}
