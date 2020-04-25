use toeiconline;
alter table comment add CONSTRAINT fk_comment_user FOREIGN KEY (userid) REFERENCES userEntity(userid);
alter table comment add CONSTRAINT fk_comment_listenguideline FOREIGN KEY (listenguidelineid) REFERENCES listenguideline(listenguidelineid);