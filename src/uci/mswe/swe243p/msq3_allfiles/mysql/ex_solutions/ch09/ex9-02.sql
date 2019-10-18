SELECT start_date, 
    DATE_FORMAT(start_date, '%b/%d/%y') AS format1, 
    DATE_FORMAT(start_date, '%c/%e/%y') AS format2, 
    DATE_FORMAT(start_date, '%l:%i %p') AS twelve_hour,
    DATE_FORMAT(start_date, '%c/%e/%y %l:%i %p') AS format3 
FROM date_sample