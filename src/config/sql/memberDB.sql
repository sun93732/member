CREATE SCHEMA if not exists member;
CREATE TABLE if not exists member.member(
	id CHAR(36),
	role CHAR(100) NOT NULL,
	`status` CHAR(100) NOT NULL,
	organisationId CHAR(36) NOT NULL,
	userId CHAR(36),
	PRIMARY KEY (id)
);