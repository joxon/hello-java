USE ex;

INSERT INTO members
VALUES (DEFAULT, 'John', 'Smith', '334 Valencia St.', 'San Francisco', 'CA', '415-942-1901');
INSERT INTO members
VALUES (DEFAULT, 'Jane', 'Doe', '872 Chetwood St.', 'Oakland', 'CA', '510-123-4567');

INSERT INTO committees
VALUES (DEFAULT, 'Book Drive');
INSERT INTO committees
VALUES (DEFAULT, 'Bicycle Coalition');

INSERT INTO members_committees
VALUES (1, 2);
INSERT INTO members_committees
VALUES (2, 1);
INSERT INTO members_committees
VALUES (2, 2);

SELECT c.committee_name, m.last_name, m.first_name
FROM committees c
  JOIN members_committees mc
    ON c.committee_id = mc.committee_id
  JOIN members m
    ON mc.member_id = m.member_id
ORDER BY c.committee_name, m.last_name, m.first_name;