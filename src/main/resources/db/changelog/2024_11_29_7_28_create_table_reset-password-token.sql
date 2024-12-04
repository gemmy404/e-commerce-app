CREATE TABLE `reset-password-token`
(
    id           INT AUTO_INCREMENT
        PRIMARY KEY,
    created_at   DATETIME(6)  NOT NULL,
    expires_at   DATETIME(6)  NOT NULL,
    token        VARCHAR(255) NOT NULL,
    validated_at DATETIME(6)  NULL,
    user_id      INT NOT NULL,
    CONSTRAINT FKiblu4cjwvyntq3ugo31klp1c6
        FOREIGN KEY (user_id) REFERENCES users (id)
);

