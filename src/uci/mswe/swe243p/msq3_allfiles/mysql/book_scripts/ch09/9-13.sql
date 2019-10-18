USE ex;

SELECT * FROM date_sample
WHERE start_date = '10:00:00';

SELECT * FROM date_sample
WHERE DATE_FORMAT(start_date, '%T') = '10:00:00';

SELECT * FROM date_sample
WHERE EXTRACT(HOUR_SECOND FROM start_date) = 100000;

SELECT * FROM date_sample
WHERE HOUR(start_date) = 9;

SELECT * FROM date_sample
WHERE EXTRACT(HOUR_MINUTE FROM start_date) BETWEEN 900 AND 1200;