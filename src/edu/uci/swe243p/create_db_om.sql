-- *************************************************************
-- This script only creates the OM (Order Management) database
-- for Murach's MySQL 3rd Edition by Joel Murach 
-- *************************************************************

-- create database
DROP DATABASE IF EXISTS om;
CREATE DATABASE om;

-- select database
USE om;

-- create tables
CREATE TABLE customers
(
  customer_id           INT             NOT NULL,
  customer_first_name   VARCHAR(50),
  customer_last_name    VARCHAR(50)     NOT NULL,
  customer_address      VARCHAR(255)    NOT NULL,
  customer_city         VARCHAR(50)     NOT NULL,
  customer_state        CHAR(2)         NOT NULL,
  customer_zip          VARCHAR(20)     NOT NULL,
  customer_phone        VARCHAR(30)     NOT NULL,
  customer_fax          VARCHAR(30),
  CONSTRAINT customers_pk 
    PRIMARY KEY (customer_id)
);

CREATE TABLE items
(
  item_id       INT             NOT NULL,
  title         VARCHAR(50)     NOT NULL,
  artist        VARCHAR(50)     NOT NULL,
  unit_price    DECIMAL(9,2)    NOT NULL,
  CONSTRAINT items_pk 
    PRIMARY KEY (item_id),
  CONSTRAINT title_artist_unq
    UNIQUE (title, artist)
);

CREATE TABLE orders
(
  order_id          INT         NOT NULL,
  customer_id       INT         NOT NULL,
  order_date        DATE        NOT NULL,
  shipped_date      DATE,
  CONSTRAINT orders_pk
    PRIMARY KEY (order_id),
  CONSTRAINT orders_fk_customers
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);

CREATE TABLE order_details
(
  order_id      INT           NOT NULL,
  item_id       INT           NOT NULL,
  order_qty     INT           NOT NULL,
  CONSTRAINT order_details_pk 
    PRIMARY KEY (order_id, item_id),
  CONSTRAINT order_details_fk_orders
    FOREIGN KEY (order_id)
    REFERENCES orders (order_id),
  CONSTRAINT order_details_fk_items
    FOREIGN KEY (item_id)
    REFERENCES items (item_id)
);

-- insert rows into tables
INSERT INTO customers VALUES 
(1,'Korah','Blanca','1555 W Lane Ave','Columbus','OH','43221','6145554435','6145553928'), 
(2,'Yash','Randall','11 E Rancho Madera Rd','Madison','WI','53707','2095551205','2095552262'), 
(3,'Johnathon','Millerton','60 Madison Ave','New York','NY','10010','2125554800','NULL'), 
(4,'Mikayla','Damion','2021 K Street Nw','Washington','DC','20006','2025555561','NULL'), 
(5,'Kendall','Mayte','4775 E Miami River Rd','Cleves','OH','45002','5135553043','NULL'), 
(6,'Kaitlin','Hostlery','3250 Spring Grove Ave','Cincinnati','OH','45225','8005551957','8005552826'), 
(7,'Derek','Chaddick','9022 E Merchant Wy','Fairfield','IA','52556','5155556130','NULL'), 
(8,'Deborah','Damien','415 E Olive Ave','Fresno','CA','93728','5595558060','NULL'), 
(9,'Karina','Lacy','882 W Easton Wy','Los Angeles','CA','90084','8005557000','NULL'), 
(10,'Kurt','Nickalus','28210 N Avenue Stanford','Valencia','CA','91355','8055550584','055556689'), 
(11,'Kelsey','Eulalia','7833 N Ridge Rd','Sacramento','CA','95887','2095557500','2095551302'), 
(12,'Anders','Rohansen','12345 E 67th Ave NW','Takoma Park','MD','24512','3385556772','NULL'), 
(13,'Thalia','Neftaly','2508 W Shaw Ave','Fresno','CA','93711','5595556245','NULL'), 
(14,'Gonzalo','Keeton','12 Daniel Road','Fairfield','NJ','07004','2015559742','NULL'), 
(15,'Ania','Irvin','1099 N Farcourt St','Orange','CA','92807','7145559000','NULL'), 
(16,'Dakota','Baylee','1033 N Sycamore Ave.','Los Angeles','CA','90038','2135554322','NULL'), 
(17,'Samuel','Jacobsen','3433 E Widget Ave','Palo Alto','CA','92711','4155553434','NULL'), 
(18,'Justin','Javen','828 S Broadway','Tarrytown','NY','10591','8005550037','NULL'), 
(19,'Kyle','Marissa','789 E Mercy Ave','Phoenix','AZ','85038','9475553900','NULL'), 
(20,'Erick','Kaleigh','Five Lakepointe Plaza, Ste 500','Charlotte','NC','28217','7045553500','NULL'), 
(21,'Marvin','Quintin','2677 Industrial Circle Dr','Columbus','OH','43260','6145558600','6145557580'), 
(22,'Rashad','Holbrooke','3467 W Shaw Ave #103','Fresno','CA','93711','5595558625','5595558495'), 
(23,'Trisha','Anum','627 Aviation Way','Manhatttan Beach','CA','90266','3105552732','NULL'), 
(24,'Julian','Carson','372 San Quentin','San Francisco','CA','94161','6175550700','NULL'), 
(25,'Kirsten','Story','2401 Wisconsin Ave NW','Washington','DC','20559','2065559115','NULL');

INSERT INTO items (item_id,title,artist,unit_price) VALUES 
(1,'Umami In Concert','Umami',17.95),
(2,'Race Car Sounds','The Ubernerds',13),
(3,'No Rest For The Weary','No Rest For The Weary',16.95),
(4,'More Songs About Structures and Comestibles','No Rest For The Weary',17.95),
(5,'On The Road With Burt Ruggles','Burt Ruggles',17.5),
(6,'No Fixed Address','Sewed the Vest Pocket',16.95),
(7,'Rude Noises','Jess & Odie',13),
(8,'Burt Ruggles: An Intimate Portrait','Burt Ruggles',17.95),
(9,'Zone Out With Umami','Umami',16.95),
(10,'Etcetera','Onn & Onn',17);

INSERT INTO orders VALUES
(19, 1, '2016-10-23', '2016-10-28'),
(29, 8, '2016-11-05', '2016-11-11'),
(32, 11, '2016-11-10', '2016-11-13'),
(45, 2, '2016-11-25', '2016-11-30'),
(70, 10, '2016-12-28', '2017-01-07'),
(89, 22, '2017-01-20', '2017-01-22'),
(97, 20, '2017-01-29', '2017-02-02'),
(118, 3, '2017-02-24', '2017-02-28'),
(144, 17, '2017-03-21', '2017-03-29'),
(158, 9, '2017-04-04', '2017-04-20'),
(165, 14, '2017-04-11', '2017-04-13'),
(180, 24, '2017-04-25', '2017-05-30'),
(231, 15, '2017-06-14', '2017-06-22'),
(242, 23, '2017-06-24', '2017-07-06'),
(264, 9, '2017-07-15', '2017-07-18'),
(298, 18, '2017-08-18', '2017-09-22'),
(321, 2, '2017-09-09', '2017-10-05'),
(381, 7, '2017-11-08', '2017-11-16'),
(392, 19, '2017-11-16', '2017-11-23'),
(413, 17, '2017-12-05', '2018-01-11'),
(442, 5, '2017-12-28', '2018-01-03'),
(479, 1, '2018-01-30', '2018-03-03'),
(491, 16, '2018-02-08', '2018-02-14'),
(494, 4, '2018-02-10', '2018-02-14'),
(523, 3, '2018-03-07', '2018-03-15'),
(548, 2, '2018-03-22', '2018-04-18'),
(550, 17, '2018-03-23', '2018-04-03'),
(601, 16, '2018-04-21', '2018-04-27'),
(606, 6, '2018-04-25', '2018-05-02'),
(607, 20, '2018-04-25', '2018-05-04'),
(624, 2, '2018-05-04', '2018-05-09'),
(627, 17, '2018-05-05', '2018-05-10'),
(630, 20, '2018-05-08', '2018-05-18'),
(631, 21, '2018-05-09', '2018-05-11'),
(651, 12, '2018-05-19', '2018-06-02'),
(658, 12, '2018-05-23', '2018-06-02'),
(687, 17, '2018-06-05', '2018-06-08'),
(693, 9, '2018-06-07', '2018-06-19'),
(703, 19, '2018-06-12', '2018-06-19'),
(773, 25, '2018-07-11', '2018-07-13'),
(778, 13, '2018-07-12', '2018-07-21'),
(796, 17, '2018-07-19', '2018-07-26'),
(800, 19, '2018-07-21', '2018-07-28'),
(802, 2, '2018-07-21', '2018-07-31'),
(824, 1, '2018-08-01', NULL),
(827, 18, '2018-08-02', NULL),
(829, 9, '2018-08-02', NULL);

INSERT INTO order_details VALUES 
(381,1,1),
(601,9,1),
(442,1,1),
(523,9,1),
(630,5,1),
(778,1,1),
(693,10,1),
(118,1,1),
(264,7,1),
(607,10,1),
(624,7,1),
(658,1,1),
(800,5,1),
(158,3,1),
(321,10,1),
(687,6,1),
(827,6,1),
(144,3,1),
(264,8,1),
(479,1,2),
(630,6,2),
(796,5,1),
(97,4,1),
(601,5,1),
(773,10,1),
(800,1,1),
(29,10,1),
(70,1,1),
(97,8,1),
(165,4,1),
(180,4,1),
(231,10,1),
(392,8,1),
(413,10,1),
(491,6,1),
(494,2,1),
(606,8,1),
(607,3,1),
(651,3,1),
(703,4,1),
(796,2,1),
(802,2,1),
(802,3,1),
(824,7,2),
(829,1,1),
(550,4,1),
(796,7,1),
(829,2,1),
(693,6,1),
(29,3,1),
(32,7,1),
(242,1,1),
(298,1,1),
(479,4,1),
(548,9,1),
(627,9,1),
(778,3,1),
(687,8,1),
(19,5,1),
(89,4,1),
(242,6,1),
(264,4,1),
(550,1,1),
(631,10,1),
(693,7,3),
(824,3,1),
(829,5,1),
(829,9,1);