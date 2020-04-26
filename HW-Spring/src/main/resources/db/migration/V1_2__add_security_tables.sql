create table users (
  id character(36) PRIMARY KEY,
  username VARCHAR(256) NOT NULL,
  password VARCHAR(256) NOT NULL,
  enabled boolean
);

create table authorities (
  user_id  VARCHAR(256) REFERENCES users(id) ON DELETE CASCADE,
  authority VARCHAR(256) NOT NULL,
  PRIMARY KEY (user_id, authority)
);
