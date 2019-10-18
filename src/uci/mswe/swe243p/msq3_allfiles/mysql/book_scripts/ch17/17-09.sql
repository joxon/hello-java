SET GLOBAL  autocommit = ON;
SET SESSION autocommit = OFF;
SET GLOBAL  autocommit = DEFAULT;

SET GLOBAL  max_connections = 90;
SET GLOBAL  max_connections = DEFAULT;

SET GLOBAL  tmp_table_size = 36700160;
SET GLOBAL  tmp_table_size = 35 * 1024 * 1024;

SELECT @@GLOBAL.autocommit, @@SESSION.autocommit;

SELECT @@autocommit;

-- reset values
SET SESSION autocommit = DEFAULT;
SET GLOBAL  tmp_table_size = DEFAULT;