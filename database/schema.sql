CREATE DATABASE `eshop`;

CREATE TABLE `eshop`.`customer` (
  `name` VARCHAR(32) NOT NULL,
  `address` VARCHAR(128) NOT NULL,
  `email` VARCHAR(128) NULL,
  PRIMARY KEY (`name`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);

LOAD DATA INFILE `data.csv`
  INTO TABLE `eshop`.`customers`
  FIELDS TERMINATED BY `:`
  LINES TERMINATED BY `\n`
  IGNORE 1 ROWS;

CREATE TABLE `eshop`.`lineitem` (
  `item` VARCHAR(32) NOT NULL,
  `quantity` INT NOT NULL,
  `order_id` VARCHAR(8) NOT NULL,
PRIMARY KEY (`order_id`));


CREATE TABLE `eshop`.`order` (
  `order_id` varchar(8) NOT NULL,
  `name` varchar(32) NOT NULL,
  `order_date` timestamp CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_id_UNIQUE` (`order_id`)
);

CREATE TABLE `order_status` (
  `order_id` varchar(8) NOT NULL,
  `delivery_id` varchar(128) NOT NULL,
  `status` varchar(45) NOT NULL,
  `status_update` timestamp DEFAULT NULL CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`)
)

