package ru.itis.service;

import ru.itis.model.PenaltyEntity;

import java.util.Date;
import java.util.List;

public interface PenaltyService {

    List<PenaltyEntity> findAllByCarId(Long carId);

    List<PenaltyEntity> findAllWhereAmountLargerThan(int minValue);

    List<PenaltyEntity> findAllOlderThanDate(Date date);

    PenaltyEntity addNewPenalty(PenaltyEntity entity);

}
