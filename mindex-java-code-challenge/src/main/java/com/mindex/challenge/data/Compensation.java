package com.mindex.challenge.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

/**
 * Compensation class is used for storing employee and salary
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compensation {

    @NotBlank
    @DBRef
    private Employee employee;

    @NotBlank
    private Double salary;

    @NotBlank
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date effectiveDate;

}
