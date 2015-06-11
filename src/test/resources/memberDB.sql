CREATE SCHEMA member;
CREATE TABLE member.member(
	memberId CHAR(100),
	role CHAR(100) NOT NULL,
	`status` CHAR(100) NOT NULL,
	organisationId CHAR(100) NOT NULL,
	userId CHAR(100),
	PRIMARY KEY (memberId)
);