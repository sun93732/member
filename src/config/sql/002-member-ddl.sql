CREATE TABLE if NOT EXISTS member.member (
	id CHAR(36),
	user_id CHAR(36) NOT NULL,
	organisation_id CHAR(36) NOT NULL,
	role VARCHAR(50) NOT NULL,
	creation_time TIMESTAMP(3) NOT NULL,
	modification_time TIMESTAMP(3) NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT unique_member UNIQUE (user_id,organisation_id)
);
