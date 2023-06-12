CREATE TABLE todo_user
(
  id    uuid         NOT NULL,
  username VARCHAR(255) NOT NULL,
  CONSTRAINT pk_todouser PRIMARY KEY (id),
  CONSTRAINT uc_todouser_username UNIQUE (username)
);

INSERT INTO todo_user (id, username)
  VALUES ('9b8b7ff4-19cc-4255-90bc-b64aa2d932ba', 'auth0|64859fbe6e2840491f1a8717');

ALTER TABLE todo
  ADD owner_id uuid;

ALTER TABLE todo
  ADD CONSTRAINT fk_todo_on_todo_user FOREIGN KEY (owner_id) REFERENCES todo_user (id);

UPDATE todo
  SET owner_id = '9b8b7ff4-19cc-4255-90bc-b64aa2d932ba'
  WHERE owner_id IS NULL;

ALTER TABLE todo
    ALTER COLUMN owner_id SET NOT NULL;

CREATE INDEX idx_todo_owner_id ON todo (owner_id);
