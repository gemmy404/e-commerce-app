CREATE TABLE categories
(
    id                 INT AUTO_INCREMENT
        PRIMARY KEY,
    created_by         INT NOT NULL,
    created_date       DATETIME(6)  NOT NULL,
    last_modified_by   INT NULL,
    last_modified_date DATETIME(6)  NULL,
    name               VARCHAR(255) NOT NULL
);