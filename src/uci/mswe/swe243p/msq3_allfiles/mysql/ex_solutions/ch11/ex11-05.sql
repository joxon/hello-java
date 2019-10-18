ALTER TABLE committees
MODIFY committee_name VARCHAR(50) NOT NULL UNIQUE;

INSERT INTO committees (committee_name)
VALUES ('Book Drive');