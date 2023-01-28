package com.mindex.challenge.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotBlank;

/**
 * Reposrting Structure to fetch number of reportee.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportingStructure {

    @NotBlank
    @DBRef
    private Employee employee;

    private Integer numberOfReports;

    @Override
    public String toString(){
        return String.format(employee +" numberOfReports = %s]",numberOfReports );
    }
}
