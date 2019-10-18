SHOW CHARSET;

SHOW CHARSET LIKE 'utf8mb4';

SHOW COLLATION;

SHOW COLLATION LIKE 'utf8mb4%';

SHOW VARIABLES LIKE 'character_set_server';

SHOW VARIABLES LIKE 'collation_server';

SHOW VARIABLES LIKE 'character_set_database';

SHOW VARIABLES LIKE 'collation_database';

SELECT table_name, table_collation
FROM information_schema.tables
WHERE table_schema = 'ap';