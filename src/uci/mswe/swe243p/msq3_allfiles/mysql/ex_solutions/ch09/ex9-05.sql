SELECT emp_name, REGEXP_SUBSTR(emp_name, '[A-Z]* ') AS first_name,
       REGEXP_SUBSTR(emp_name, '[A-Z]* [A-Z]*|[A-Z]*[-|\'][A-Z]*|[A-Z]*',
           REGEXP_INSTR(emp_name, ' ') + 1) AS last_name      
FROM string_sample