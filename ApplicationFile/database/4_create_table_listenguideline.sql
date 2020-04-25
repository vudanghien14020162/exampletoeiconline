use toeiconline;

CREATE TABLE listenguideline (
	listenguidelineid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(255) null ,
	image VARCHAR(255) null ,
	content VARCHAR(300) null ,
	createddate TIMESTAMP null ,
	modifileday TIMESTAMP null
);

CREATE TABLE comment (
    commentid bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(255) null,
    userId BIGINT null ,
    listenguidelineid BIGINT null ,
    createddate TIMESTAMP null
);