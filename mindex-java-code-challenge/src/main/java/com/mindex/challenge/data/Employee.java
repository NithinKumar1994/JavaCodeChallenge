package com.mindex.challenge.data;

import lombok.*;
import org.springframework.data.annotation.Id;


import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    private String employeeId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String position;

    @NotBlank
    private String department;

    private List<Employee> directReports;


    @Override
    public String toString(){
        return String.format("Employee[ id=%s, firstName= '%s' , lastName= '%s' , position= %s , department =%s ]"
                ,employeeId, firstName, lastName, position, department );
    }

}
