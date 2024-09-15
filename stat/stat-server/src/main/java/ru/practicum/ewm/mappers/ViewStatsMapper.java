package ru.practicum.ewm.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.practicum.dto.StatisticResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер для преобразования результата запроса к таблице статистики в объект ViewStats.
 */
@Component
public class ViewStatsMapper implements RowMapper<StatisticResponse> {

    /**
     * Метод для маппинга строки результата запроса в объект ViewStats.
     *
     * @param rs объект ResultSet с результатами запроса
     * @param rowNum номер строки результата запроса
     * @return объект ViewStatsDto
     * @throws SQLException если произошла ошибка при доступе к данным в ResultSet
     */
    @Override
    public StatisticResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new StatisticResponse(
                rs.getString("app"),
                rs.getString("uri"),
                rs.getLong("hits")
        );

    }
}