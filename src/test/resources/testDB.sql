CREATE SCHEMA member AUTHORIZATION sa

CREATE TABLE member (
	id CHAR(36),
	user_id CHAR(36) NOT NULL,
	organisation_id CHAR(36) NOT NULL,
	role VARCHAR(50) NOT NULL,
	creationTime TIMESTAMP(3) NOT NULL,
	modificationTime TIMESTAMP(3) NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT unique_member UNIQUE (user_Id,organisation_Id)
);