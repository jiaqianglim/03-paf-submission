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

CREATE TABLE `eshop`.`order_status` (
  `order_id` VARCHAR(8) NOT NULL,
  `delivery_id` VARCHAR(128) NOT NULL,
  `name` VARCHAR(32) NOT NULL,
  `order_date` NULL TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`),
  INDEX `name_idx` (`name` ASC) VISIBLE,
  CONSTRAINT `name`
    FOREIGN KEY (`name`)
    REFERENCES `eshop`.`customers` (`name`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `delivery_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `eshop`.`order_status` (`order_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `order_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `eshop`.`lineitem` (`order_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);

CREATE TABLE `eshop`.`order` (
  `order_id` varchar(8) NOT NULL,
  `name` varchar(32) NOT NULL,
  `order_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_id_UNIQUE` (`order_id`)
);

