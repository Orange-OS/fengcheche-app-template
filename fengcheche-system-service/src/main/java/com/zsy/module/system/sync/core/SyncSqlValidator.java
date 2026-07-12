package com.zsy.module.system.sync.core;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class SyncSqlValidator {

    private static final Pattern IDENTIFIER = Pattern.compile("^[A-Za-z0-9_]+$");
    private static final Pattern TABLE_IDENTIFIER = Pattern.compile("^[A-Za-z0-9_]+(\\.[A-Za-z0-9_]+)?$");

    private SyncSqlValidator() {
    }

    public static void validateIdentifier(String value, String label) {
        if (StrUtil.isBlank(value)) {
            throw new IllegalArgumentException(label + "不能为空");
        }
        if (!IDENTIFIER.matcher(value).matches()) {
            throw new IllegalArgumentException(label + "不合法");
        }
    }

    public static void validateTableIdentifier(String value, String label) {
        if (StrUtil.isBlank(value)) {
            throw new IllegalArgumentException(label + "不能为空");
        }
        if (!TABLE_IDENTIFIER.matcher(value).matches()) {
            throw new IllegalArgumentException(label + "不合法");
        }
    }

    public static List<String> parseColumnList(String columnList) {
        if (StrUtil.isBlank(columnList)) {
            return new ArrayList<>();
        }
        String trimmed = columnList.trim();
        if ("*".equals(trimmed)) {
            return new ArrayList<>();
        }
        String[] parts = trimmed.split(",");
        List<String> columns = new ArrayList<>();
        for (String part : parts) {
            String col = part.trim();
            if (StrUtil.isBlank(col)) {
                continue;
            }
            if (!IDENTIFIER.matcher(col).matches()) {
                throw new IllegalArgumentException("列名不合法: " + col);
            }
            columns.add(col);
        }
        return columns;
    }

    public static void ensureContains(List<String> columns, String requiredColumn) {
        if (StrUtil.isBlank(requiredColumn)) {
            return;
        }
        String requiredLower = requiredColumn.toLowerCase(Locale.ROOT);
        for (String column : columns) {
            if (column.toLowerCase(Locale.ROOT).equals(requiredLower)) {
                return;
            }
        }
        throw new IllegalArgumentException("列清单缺少必要字段: " + requiredColumn);
    }
}
