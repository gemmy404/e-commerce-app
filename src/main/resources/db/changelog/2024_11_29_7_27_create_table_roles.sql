CREATE TABLE roles
(
    id                 INT AUTO_INCREMENT
        PRIMARY KEY,
    created_date       DATETIME(6)  NOT NULL,
    last_modified_date DATETIME(6)  NULL,
    name               VARCHAR(255) NOT NULL,
    CONSTRAINT UK8sewwnpamngi6b1dwaa88askk
        UNIQUE (name)
);