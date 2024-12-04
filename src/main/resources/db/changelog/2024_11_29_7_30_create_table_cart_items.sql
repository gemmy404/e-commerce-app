CREATE TABLE `cart-items`
(
    id                 INT AUTO_INCREMENT
    PRIMARY KEY,
    created_by         INT         NOT NULL,
    created_date       DATETIME(6) NOT NULL,
    last_modified_by   INT         NULL,
    last_modified_date DATETIME(6) NULL,
    price              DOUBLE      NOT NULL,
    quantity           INT         NOT NULL,
    product_id         INT         NOT NULL,
    user_id            INT         NOT NULL,
    CONSTRAINT FK6jykus0c9wfmras5s9xxl9hfl
        FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT FK7onorwqn7sddwxla6119lmk2w
        FOREIGN KEY (product_id) REFERENCES products (id)
);

