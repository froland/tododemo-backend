CREATE TABLE todo
(
  id          UUID       NOT NULL,
  done        BOOLEAN      NOT NULL,
  description VARCHAR(255) NOT NULL,
  CONSTRAINT pk_todo PRIMARY KEY (id)
);
