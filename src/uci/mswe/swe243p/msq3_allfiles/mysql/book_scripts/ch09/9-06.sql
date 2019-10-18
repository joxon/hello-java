USE ex;

SELECT * 
FROM float_sample
WHERE float_value = 1;

SELECT * 
FROM float_sample
WHERE float_value BETWEEN 0.99 AND 1.01;

SELECT *
FROM float_sample
WHERE ROUND(float_value, 2) = 1.00;