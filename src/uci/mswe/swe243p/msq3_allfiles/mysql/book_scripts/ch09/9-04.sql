USE ex;

SELECT emp_name,
    SUBSTRING_INDEX(emp_name, ' ', 1) AS first_name,
    SUBSTRING_INDEX(emp_name, ' ', -1) AS last_name
FROM string_sample;

SELECT emp_name,
    LOCATE(' ', emp_name) AS first_space,
    LOCATE(' ', emp_name, LOCATE(' ', emp_name) + 1) AS second_space
FROM string_sample;

SELECT emp_name,
    SUBSTRING(emp_name, 1, LOCATE(' ', emp_name) - 1) AS first_name,
    SUBSTRING(emp_name, LOCATE(' ', emp_name) + 1) AS last_name
FROM string_sample;