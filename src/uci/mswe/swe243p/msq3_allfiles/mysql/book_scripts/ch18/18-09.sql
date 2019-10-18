-- preferred technique
ALTER USER john IDENTIFIED BY 'pa55word';

ALTER USER USER() IDENTIFIED BY 'secret';

ALTER USER IF EXISTS john PASSWORD EXPIRE INTERVAL 90 DAY;

-- alternate technique
SET PASSWORD FOR john = 'pa55word';

SET PASSWORD = 'secret';

SELECT Host, User
FROM mysql.user
WHERE authentication_string = '';