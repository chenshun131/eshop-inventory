CREATE DATABASE IF NOT EXISTS eshop;
USE eshop;
CREATE TABLE user(name VARCHAR(255), age INT);
INSERT INTO user VALUES('张三', 25);

CREATE TABLE product_inventory(product_id INT, inventory_cnt INT);