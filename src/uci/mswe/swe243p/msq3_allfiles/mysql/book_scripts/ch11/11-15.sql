CREATE DATABASE ar CHARSET latin1 COLLATE latin1_general_ci;

ALTER DATABASE ar CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

ALTER DATABASE ar CHARSET utf8mb4;

ALTER DATABASE ar COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE employees
(
  emp_id        INT          PRIMARY KEY,
  emp_name      VARCHAR(25)
)
CHARSET latin1 COLLATE latin1_general_ci;

ALTER TABLE employees 
CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE employees
(
  emp_id        INT          PRIMARY KEY,
  emp_name      VARCHAR(25)  CHARSET latin1 COLLATE latin1_general_ci
);

ALTER TABLE employees
MODIFY emp_name VARCHAR(25) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci;