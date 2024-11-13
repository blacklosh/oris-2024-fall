package ru.itis.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.config.ModuleConfiguration;
import ru.itis.model.CarEntity;
import ru.itis.service.CarService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Slf4j
public class CarServiceImpl implements CarService {

    private final JdbcTemplate jdbcTemplate = ModuleConfiguration.jdbcTemplate();

    private static final String SQL_SELECT_ALL = "select * from CAR";

    private static final String SQL_SELECT_BY_ID = "select * from CAR where id = ?";

    private static final String SQL_SELECT_BY_TITLE = "select * from CAR where title = ?";

    private static final String SQL_INSERT = "insert into CAR(title) values (?)";

    private static final String SQL_DELETE = "delete from CAR where id = ?";

    private static final String SQL_UPDATE = "update CAR set title = ? where id = ?";

    private final CarRowMapper carRowMapper = new CarRowMapper();

    @Override
    public List<CarEntity> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, carRowMapper);
    }

    @Override
    public CarEntity findCarById(Long id) {
        log.info("Ищем машину по id = {}", id);
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[] {id}, carRowMapper);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Не нашлось машины с номером {}", id);
            return null;
        }
    }

    @Override
    public CarEntity findCarByTitle(String title) {
        log.info("Ищем машину по title = {}", title);
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_TITLE, new Object[] { title }, carRowMapper);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Не нашлось машины с названием {}", title);
            return null;
        }
    }

    @Override
    public CarEntity saveNewCar(CarEntity car) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_INSERT, new String[] {"id"});
            ps.setString(1, car.getTitle());
            return ps;
        }, holder);
        Long id = Objects.requireNonNull(holder.getKey()).longValue();
        return findCarById(id);
    }

    @Override
    public boolean deleteCarById(Long id) {
        return jdbcTemplate.update(SQL_DELETE, id) == 1;
    }

    @Override
    public CarEntity updateCarById(CarEntity car, Long id) {
        int n = jdbcTemplate.update(SQL_UPDATE, car.getTitle(), id);
        if(n == 0) {
            log.warn("Машины по id = {} не существует!", id);
            return null;
        }
        return findCarById(id);
    }

    private static final class CarRowMapper implements RowMapper<CarEntity> {

        @Override
        public CarEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return CarEntity.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .build();
        }
    }
}
