USE ex;

SELECT customer_first_name, customer_last_name
FROM customers c JOIN employees e 
    ON c.customer_first_name = e.first_name 
   AND c.customer_last_name = e.last_name;