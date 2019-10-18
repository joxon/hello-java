-- change the password reuse interval for user john so the password doesn't cause a conflict
ALTER USER IF EXISTS john PASSWORD REUSE INTERVAL 0 DAY;

-- create the users
CREATE USER IF NOT EXISTS john IDENTIFIED BY 'sesame';
CREATE USER IF NOT EXISTS jane IDENTIFIED BY 'sesame';
CREATE USER IF NOT EXISTS jim IDENTIFIED BY 'sesame';
CREATE USER IF NOT EXISTS joel@localhost IDENTIFIED BY 'sesame';

-- create the roles
CREATE ROLE IF NOT EXISTS developer, manager, user;

-- grant privileges to the developer role
GRANT ALL ON *.* TO developer WITH GRANT OPTION;

-- grant privileges to the manager role
GRANT SELECT, INSERT, UPDATE, DELETE ON ap.* TO manager WITH GRANT OPTION;

-- grant privileges to user role
GRANT SELECT, INSERT, UPDATE, DELETE ON ap.vendors TO user;
GRANT SELECT, INSERT, UPDATE, DELETE ON ap.invoices TO user;
GRANT SELECT, INSERT, UPDATE, DELETE ON ap.invoice_line_items TO user;
GRANT SELECT ON ap.general_ledger_accounts TO user;
GRANT SELECT ON ap.terms TO user;

-- assign users to roles 
GRANT developer to joel@localhost;
GRANT manager TO jim;
GRANT user TO john, jane;

-- set default roles for users 
SET DEFAULT ROLE developer to joel@localhost;
SET DEFAULT ROLE manager to jim;
SET DEFAULT ROLE user TO john, jane;
