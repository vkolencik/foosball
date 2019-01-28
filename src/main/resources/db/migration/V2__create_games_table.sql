CREATE TABLE games (
  id SERIAL PRIMARY KEY NOT NULL,
  player_a_1 INTEGER REFERENCES players(id) NOT NULL,
  player_a_2 INTEGER REFERENCES players(id) NOT NULL,
  player_b_1 INTEGER REFERENCES players(id) NOT NULL,
  player_b_2 INTEGER REFERENCES players(id) NOT NULL,
  winning_team CHAR(1) CHECK (winning_team IN ('A', 'B'))
)
