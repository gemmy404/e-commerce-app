CREATE TABLE users
(
    id                 INT AUTO_INCREMENT
        PRIMARY KEY,
    created_date       DATETIME(6)  NOT NULL,
    email              VARCHAR(255) NOT NULL,
    first_name         VARCHAR(255) NOT NULL,
    last_modified_date DATETIME(6)      NULL,
    last_name          VARCHAR(255) NOT NULL,
    password           VARCHAR(255) NOT NULL,
    phone              VARCHAR(255) NOT NULL,
    profile_picture    VARCHAR(255) NULL,
    CONSTRAINT UK6dotkott2kjsp8vw4d0m25fb7
        UNIQUE (email)
);