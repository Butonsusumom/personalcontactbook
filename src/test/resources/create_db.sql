CREATE DATABASE phone_book;

CREATE TABLE phone_book.user (
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  fullname varchar(45),
  login varchar(45),
  password varchar(100),
  role varchar(45),
  INDEX(login)
);

CREATE TABLE phone_book.contact (
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  iduser BIGINT NOT NULL,
  lastname varchar(45),
  name varchar(45),
  middlename varchar(45),
  mobilephone varchar(45),
  homephone varchar(45),
  address varchar(45),
  email varchar(45),
  INDEX(lastName)
);
