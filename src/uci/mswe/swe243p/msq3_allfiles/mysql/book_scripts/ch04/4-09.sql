USE ex;

SELECT department_name, d.department_number, last_name
FROM departments d 
    LEFT JOIN employees e
    ON d.department_number = e.department_number
ORDER BY department_name;

SELECT department_name, e.department_number, last_name
FROM departments d 
    RIGHT JOIN employees e
    ON d.department_number = e.department_number
ORDER BY department_name;

SELECT department_name, last_name, project_number
FROM departments d
    LEFT JOIN employees e
        ON d.department_number = e.department_number
    LEFT JOIN projects p
        ON e.employee_id = p.employee_id
ORDER BY department_name, last_name;

SELECT department_name, last_name, project_number
FROM departments d
    JOIN employees e
        ON d.department_number = e.department_number
    LEFT JOIN projects p
        ON e.employee_id = p.employee_id
ORDER BY department_name, last_name;