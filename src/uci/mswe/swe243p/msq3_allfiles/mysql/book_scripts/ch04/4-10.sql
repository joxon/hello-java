SELECT invoice_number, vendor_name
FROM vendors 
    JOIN invoices USING (vendor_id)
ORDER BY invoice_number;

SELECT department_name, last_name, project_number
FROM departments
    JOIN employees USING (department_number)
    LEFT JOIN projects USING (employee_id)
ORDER BY department_name;