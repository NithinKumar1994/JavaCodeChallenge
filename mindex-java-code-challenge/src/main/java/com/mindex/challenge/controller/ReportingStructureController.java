package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.exceptions.EmployeeNotFoundException;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    ReportingStructureService reportingStructureService;

    @GetMapping("/reporting/{id}")
    public ReportingStructure getNumberOfReports(@PathVariable String id) throws EmployeeNotFoundException {
        LOG.debug("GFetching reporting structure for employeeId [{}]", id);

        return reportingStructureService.getNumberOfReports(id);
    }
}
