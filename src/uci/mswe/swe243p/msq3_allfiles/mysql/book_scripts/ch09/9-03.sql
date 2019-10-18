USE ex;

SELECT * 
FROM string_sample
ORDER BY emp_id;

SELECT * 
FROM string_sample
ORDER BY CAST(emp_id AS SIGNED);

SELECT * 
FROM string_sample
ORDER BY emp_id + 0;

SELECT LPAD(emp_id, 2, '0') AS emp_id, emp_name
FROM string_sample
ORDER BY emp_id;