CREATE TABLE products
(
    id                 INT AUTO_INCREMENT
        PRIMARY KEY,
    created_by         INT          NOT NULL,
    created_date       DATETIME(6)  NOT NULL,
    last_modified_by   INT NULL,
    last_modified_date DATETIME(6)  NULL,
    description        VARCHAR(255) NOT NULL,
    image              VARCHAR(255) NULL,
    is_available       BIT          NOT NULL,
    name               VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    stock_quantity     INT          NOT NULL,
    category_id        INT          NOT NULL,
    CONSTRAINT FKog2rp4qthbtt2lfyhfo32lsw9
        FOREIGN KEY (category_id) REFERENCES categories (id)
);

