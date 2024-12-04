CREATE TABLE wish_list
(
    id                 INT AUTO_INCREMENT
        PRIMARY KEY,
    created_by         INT         NOT NULL,
    created_date       DATETIME(6) NOT NULL,
    last_modified_by   INT         null,
    last_modified_date DATETIME(6) null,
    product_id         INT         NOT NULL,
    user_id            INT         NOT NULL,
    CONSTRAINT FK4rinsnljl392d54deeat54w1f
        FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT FKit8ap20bpapw291y78egje6f3
        FOREIGN KEY (user_id) REFERENCES users (id)
);

