CREATE TABLE userEntity (
	userid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) ,
	password VARCHAR(255),
	fullname VARCHAR(300),
	createddate TIMESTAMP
);
CREATE TABLE roleEntity (
	roleid bigint NOT NULL PRIMARY KEY,
	name VARCHAR(255)
);