CREATE TABLE orders
(
    id           INT AUTO_INCREMENT
        PRIMARY KEY,
    delivered_at DATE         NOT NULL,
    ordered_at   DATE         NOT NULL,
    state        VARCHAR(255) NOT NULL,
    total_price  DOUBLE       NOT NULL,
    user_id      INT          NOT NULL,
    CONSTRAINT FK32ql8ubntj5uh44ph9659tiih
       FOREIGN KEY (user_id) REFERENCES users (id)
);

