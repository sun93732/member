CREATE SCHEMA if not exists member;
CREATE TABLE if not exists member.member(
	id CHAR(100),
	role CHAR(100) NOT NULL,
	`status` CHAR(100) NOT NULL,
	organisationId CHAR(100) NOT NULL,
	userId CHAR(100),
	PRIMARY KEY (id)
);