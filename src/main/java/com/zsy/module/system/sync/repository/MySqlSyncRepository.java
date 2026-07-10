package com.zsy.module.system.sync.repository;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.zsy.module.system.sync.domain.entity.SyncConfigEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MySqlSyncRepository {

    private final JdbcTemplate jdbcTemplate;

    public MySqlSyncRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @DS("master")
    public List<Map<String, Object>> queryBatch(SyncConfigEntity config, String lastVersion, String lastPk, int batchSize, String selectColumns) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(selectColumns).append(" FROM ").append(config.getSourceTable());
        List<Object> params = new ArrayList<>();
        if (StrUtil.isNotBlank(lastVersion)) {
            sql.append(" WHERE (").append(config.getVersionColumn()).append(" > ?");
            params.add(lastVersion);
            if (StrUtil.isNotBlank(lastPk)) {
                sql.append(" OR (").append(config.getVersionColumn()).append(" = ? AND ")
                        .append(config.getPrimaryKeyColumn()).append(" > ?))");
                params.add(lastVersion);
                params.add(lastPk);
            } else {
                sql.append(")");
            }
        }
        sql.append(" ORDER BY ").append(config.getVersionColumn()).append(" ASC, ")
                .append(config.getPrimaryKeyColumn()).append(" ASC");
        sql.append(" LIMIT ?");
        params.add(batchSize);
        RowMapper<Map<String, Object>> rowMapper = new ColumnMapRowMapper();
        return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
    }
}
