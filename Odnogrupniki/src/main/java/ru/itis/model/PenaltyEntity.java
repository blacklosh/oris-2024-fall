package ru.itis.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PenaltyEntity {

    private Long id;

    private Long carId;

    private Integer amount;

    private Date dayTime;

}
