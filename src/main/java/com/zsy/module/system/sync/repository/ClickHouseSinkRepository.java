package com.zsy.module.system.sync.repository;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ClickHouseSinkRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClickHouseSinkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @DS("slave_1")
    public void batchInsert(String targetTable, List<String> columns, List<Map<String, Object>> rows) {
        if (rows.isEmpty()) {
            return;
        }
        if (columns == null || columns.isEmpty()) {
            throw new IllegalArgumentException("列清单不能为空");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(targetTable)
                .append(" (").append(String.join(",", columns)).append(") VALUES (");
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                sql.append(",");
            }
            sql.append("?");
        }
        sql.append(")");

        List<Object[]> batchArgs = rows.stream().map(row -> {
            Object[] values = new Object[columns.size()];
            for (int i = 0; i < columns.size(); i++) {
                values[i] = row.get(columns.get(i));
            }
            return values;
        }).collect(Collectors.toList());

        jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
    }
}
