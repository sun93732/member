CREATE SCHEMA member AUTHORIZATION sa

CREATE TABLE member(
    memberId varchar(100) NOT NULL,
    role varchar(100) NOT NULL,
    status varchar(100) NOT NULL,
    organisationId varchar(100) NOT NULL,
    userId varchar(100),
    PRIMARY KEY (memberId)
)