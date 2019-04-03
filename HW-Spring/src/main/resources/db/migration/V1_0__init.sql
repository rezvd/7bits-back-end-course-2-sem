CREATE TABLE task(
  id character(36) PRIMARY KEY,
  text varchar NOT NULL,
  status varchar NOT NULL,
  createdAt timestamp NOT NULL
);