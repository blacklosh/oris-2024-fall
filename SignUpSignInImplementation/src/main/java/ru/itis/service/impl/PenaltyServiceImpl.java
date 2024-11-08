package ru.itis.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.config.ModuleConfiguration;
import ru.itis.model.PenaltyEntity;
import ru.itis.service.PenaltyService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Slf4j
public class PenaltyServiceImpl implements PenaltyService {

    private final JdbcTemplate jdbcTemplate = ModuleConfiguration.jdbcTemplate();

    private static final String SQL_SELECT_BY_DATE = "select * from PENALTY where dayTime < ?";
    private static final String SQL_SELECT_BY_AMOUNT = "select * from PENALTY where amount > ? order by amount desc";
    private static final String SQL_SELECT_BY_CAR_ID = "select * from PENALTY where car_id = ?";

    private final PenaltyRowMapper penaltyRowMapper = new PenaltyRowMapper();

    @Override
    public List<PenaltyEntity> findAllByCarId(Long carId) {
        return jdbcTemplate.query(SQL_SELECT_BY_CAR_ID, new Object[]{carId}, penaltyRowMapper);
    }

    @Override
    public List<PenaltyEntity> findAllWhereAmountLargerThan(int minValue) {
        return jdbcTemplate.query(SQL_SELECT_BY_AMOUNT, new Object[]{minValue}, penaltyRowMapper);
    }

    @Override
    public List<PenaltyEntity> findAllOlderThanDate(Date date) {
        return jdbcTemplate.query(SQL_SELECT_BY_DATE, new Object[]{date}, penaltyRowMapper);
    }

    @Override
    public PenaltyEntity addNewPenalty(PenaltyEntity entity) {
        return null;
    }

    private static final class PenaltyRowMapper implements RowMapper<PenaltyEntity> {
        @Override
        public PenaltyEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return PenaltyEntity.builder()
                    .id(rs.getLong("id"))
                    .carId(rs.getLong("car_id"))
                    .amount(rs.getInt("amount"))
                    .dayTime(rs.getDate("daytime"))
                    .build();
        }
    }
}
