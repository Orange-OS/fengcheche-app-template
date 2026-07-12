-- 微服务分库初始化脚本。
-- 每个服务连接自己的业务库，XXL-JOB 调度中心继续使用独立的 xxl_job 库。
CREATE DATABASE IF NOT EXISTS `fengcheche_system` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE DATABASE IF NOT EXISTS `fengcheche_life` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE DATABASE IF NOT EXISTS `fengcheche_report` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE DATABASE IF NOT EXISTS `xxl_job` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

