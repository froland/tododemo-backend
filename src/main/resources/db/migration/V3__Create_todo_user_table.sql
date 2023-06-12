CREATE TABLE todo_user
(
    id       uuid         NOT NULL,
    username VARCHAR(255) NOT NULL,
    CONSTRAINT pk_todo_user PRIMARY KEY (id),
    CONSTRAINT uc_todo_user_username UNIQUE (username)
);

INSERT INTO todo_user (id, username)
VALUES ('3e5a8c12-6e2b-44b8-a196-030d26c19ff5', 'auth0|64874274c3df064ceecbfe44');

ALTER TABLE todo ADD owner_id uuid;
UPDATE todo
  SET owner_id = '3e5a8c12-6e2b-44b8-a196-030d26c19ff5'
  WHERE owner_id IS NULL;
ALTER TABLE todo
  ALTER COLUMN owner_id SET NOT NULL;

ALTER TABLE todo
    ADD CONSTRAINT fk_todo_owner_id FOREIGN KEY (owner_id) REFERENCES todo_user (id);
CREATE INDEX idx_todo_owner_id ON todo (owner_id);
