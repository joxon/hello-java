USE ap;

SELECT *
INTO OUTFILE '/ProgramData/MySQL/MySQL Server 8.0/Uploads/vendor_contacts_tab.txt'
FROM vendor_contacts;

SELECT *
INTO OUTFILE '/ProgramData/MySQL/MySQL Server 8.0/Uploads/vendor_contacts_comma.txt'
FIELDS TERMINATED BY ','
       ENCLOSED BY '"'
       ESCAPED BY '\\'    
FROM vendor_contacts;
