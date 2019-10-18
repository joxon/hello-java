SHOW ENGINES;

SHOW VARIABLES LIKE 'default_storage_engine';

SELECT table_name, engine
FROM information_schema.tables
WHERE table_schema = 'ap';