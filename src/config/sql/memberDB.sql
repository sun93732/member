CREATE SCHEMA if not exists member;
CREATE TABLE if not exists member.member(
	`id` CHAR(36),
	`userId` CHAR(36) NOT NULL,
	`organisationId` CHAR(36) NOT NULL,
	`role` CHAR(100) NOT NULL,
	`creationTime` TIMESTAMP(3),
	`modificationTime` TIMESTAMP(3),
	PRIMARY KEY (id)
);
ALTER TABLE member.member
  ADD CONSTRAINT unique_member UNIQUE(userId, organisationId);