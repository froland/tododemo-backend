CREATE TABLE todo_user
(
    id       uuid         NOT NULL,
    username VARCHAR(255) NOT NULL,
    CONSTRAINT pk_todo_user PRIMARY KEY (id),
    CONSTRAINT uc_todo_user_username UNIQUE (username)
);

INSERT INTO todo_user (id, username)
VALUES ('07efb060-7f62-4bec-bcd2-75759ceb7746', 'auth0|648713ab4e05c893bacb4f49');

ALTER TABLE todo ADD owner_id uuid;
UPDATE todo SET owner_id='07efb060-7f62-4bec-bcd2-75759ceb7746' WHERE owner_id IS NULL;
ALTER TABLE todo ALTER COLUMN owner_id SET NOT NULL;
ALTER TABLE todo
    ADD CONSTRAINT fk_todo_todo_user_id FOREIGN KEY (owner_id) REFERENCES todo_user (id);
