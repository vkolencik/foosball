-- The database is denormalized in that a player's number of wins and losses are stored separately.
-- This is done to make the retrieval fast and is easy to keep consistent with the game entries.

ALTER TABLE players ADD COLUMN wins INT NOT NULL DEFAULT 0;
ALTER TABLE players ADD COLUMN losses INT NOT NULL DEFAULT 0;
