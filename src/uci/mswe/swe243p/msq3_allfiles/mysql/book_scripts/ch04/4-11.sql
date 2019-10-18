USE ap;

SELECT invoice_number, vendor_name
FROM vendors 
    NATURAL JOIN invoices
ORDER BY invoice_number;
 
USE ex;

SELECT department_name AS dept_name, last_name, project_number
FROM departments
    NATURAL JOIN employees
    LEFT JOIN projects USING (employee_id)
ORDER BY department_name;