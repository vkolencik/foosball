CREATE TABLE players (
  id SERIAL PRIMARY KEY NOT NULL,
  nickname VARCHAR NOT NULL UNIQUE,
  active BOOLEAN NOT NULL DEFAULT true,
  wins INT NOT NULL DEFAULT 0,
  losses INT NOT NULL DEFAULT 0
);