CREATE SCHEMA contract AUTHORIZATION sa

CREATE TABLE contract (
    contract_id                     CHAR(36) NOT NULL,
    partner                         VARCHAR(50) NOT NULL,
    merchant_id                     VARCHAR(36) NULL,
    facility                        VARCHAR(50) NOT NULL,
    status                          VARCHAR(10) NOT NULL,
    creation_date                   TIMESTAMP NOT NULL,
    modification_date               TIMESTAMP NOT NULL,
    
    CONSTRAINT pk_contract PRIMARY KEY (contract_id)
)

CREATE TABLE contract_token (
    contract_token_id   CHAR(36) NOT NULL,
    contract_id         CHAR(36) NOT NULL,
    token_id            CHAR(36) NOT NULL,
     
    CONSTRAINT pk_contract_token PRIMARY KEY (contract_token_id),
    CONSTRAINT fk_contract_token_1 FOREIGN KEY (contract_id) REFERENCES contract.contract(contract_id)
)