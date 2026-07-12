# 数据库拆分说明

本目录用于微服务拆库初始化。

## 服务与数据库

- `fengcheche-system-service` -> `fengcheche_system`
- `fengcheche-life-service` -> `fengcheche_life`
- `fengcheche-report-service` -> `fengcheche_report`
- `fengcheche-job-admin` -> `xxl_job`

## 初始化顺序

1. 执行 `00-create-databases.sql` 创建所有库。
2. 执行 `fengcheche-system.sql` 初始化系统服务表。
3. 执行 `fengcheche-life.sql` 初始化生活服务表。
4. 执行 `fengcheche-report.sql` 初始化报表服务库。
5. 执行 `xxl-job-admin.sql` 初始化 XXL-JOB 调度中心表。

如果使用 `deploy/docker-compose.yaml` 首次启动 MySQL，`deploy/sql` 会挂载到 `/docker-entrypoint-initdb.d` 自动执行。已有 `deploy/mysql/mysql-data` 数据目录时，MySQL 不会重复执行初始化脚本，需要手动导入。
