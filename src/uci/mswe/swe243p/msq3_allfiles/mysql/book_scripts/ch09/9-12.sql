USE ex;

SELECT * 
FROM date_sample
WHERE start_date = '2018-02-28';

SELECT * 
FROM date_sample
WHERE start_date >= '2018-02-28' AND start_date < '2018-03-01';

SELECT * 
FROM date_sample
WHERE MONTH(start_date) = 2 AND 
      DAYOFMONTH(start_date) = 28 AND 
      YEAR(start_date) = 2018;
      
SELECT * 
FROM date_sample
WHERE DATE_FORMAT(start_date, '%m-%d-%Y') = '02-28-2018';