-- drop users
DROP USER IF EXISTS joel@localhost;
DROP USER IF EXISTS jane;
DROP USER IF EXISTS anne@localhost;
DROP USER IF EXISTS jim;
DROP USER IF EXISTS john;

CREATE USER joel@localhost IDENTIFIED BY 'sesame';

CREATE USER IF NOT EXISTS jane IDENTIFIED BY 'sesame';    -- creates jane@%

CREATE USER anne@localhost PASSWORD EXPIRE;

CREATE USER jim IDENTIFIED BY 'sesame'
PASSWORD HISTORY 5;

CREATE USER john IDENTIFIED BY 'sesame'
PASSWORD REUSE INTERVAL 365 DAY;

RENAME USER joel@localhost TO joelmurach@localhost;

DROP USER joelmurach@localhost;

DROP USER jane;                                          -- drops jane@%
