use toeiconline;

alter table userEntity add column roleid bigint;

alter table userEntity add CONSTRAINT fk_user_role FOREIGN KEY (roleid) REFERENCES roleEntity(roleid);