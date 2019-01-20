ALTER TABLE players DROP CONSTRAINT players_pkey;
ALTER TABLE players ADD COLUMN id SERIAL;
ALTER TABLE players ADD CONSTRAINT players_nickname_unique UNIQUE (nickname);
