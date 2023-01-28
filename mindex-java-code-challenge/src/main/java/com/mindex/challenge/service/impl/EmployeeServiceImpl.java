package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.exceptions.EmployeeNotFoundException;
import com.mindex.challenge.service.EmployeeService;
import com.mongodb.MongoWriteException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    Employee employee;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        try {
            employeeRepository.insert(employee);
        } catch (
                MongoWriteException e) {
            LOG.error("An error occured when inserting into MongoDB {}", e.getMessage(), e);
        }

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Fetching employee with id [{}]", id);

        if (StringUtils.isNotBlank(id)) {
            employee = employeeRepository.findByEmployeeId(id);
        }
        if (employee == null) {
            throw new EmployeeNotFoundException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        if (!StringUtils.isNotBlank(employee.getEmployeeId())) {
            throw new EmployeeNotFoundException("Invalid employeeId: " + employee.getEmployeeId());
        }
        return employeeRepository.save(employee);
    }
}
