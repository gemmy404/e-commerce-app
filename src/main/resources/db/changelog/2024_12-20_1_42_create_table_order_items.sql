CREATE TABLE order_items
(
    id                 INT AUTO_INCREMENT
        primary key,
    created_by         INT         NOT NULL,
    created_date       DATETIME(6) NOT NULL,
    last_modified_by   INT         NULL,
    last_modified_date DATETIME(6) NULL,
    price              DOUBLE      NULL,
    quantity           INT         NULL,
    order_id           INT         NOT NULL,
    product_id         INT         NOT NULL,
    CONSTRAINT FKbioxgbv59vetrxe0ejfubep1w
        FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT FKocimc7dtr037rh4ls4l95nlfi
        FOREIGN KEY (product_id) REFERENCES products (id)
);

