DROP DATABASE IF EXISTS pandonia;
CREATE DATABASE pandonia;

USE pandonia;

CREATE TABLE `groups`
(
    id   INT          NOT NULL
        PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT groups_pk_2
        UNIQUE (name)
);

CREATE TABLE players
(
    id       VARCHAR(36)   NOT NULL
        PRIMARY KEY,
    group_id INT DEFAULT 1 NOT NULL,
    coins    INT DEFAULT 0 NOT NULL,
    CONSTRAINT players_groups_id_fk
        FOREIGN KEY (group_id) REFERENCES `groups` (id)
);

INSERT INTO `groups` (id, name)
VALUES (1, 'DEFAULT');
