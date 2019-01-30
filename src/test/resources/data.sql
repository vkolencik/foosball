DELETE FROM games;
DELETE FROM players;

INSERT INTO players (nickname, active) VALUES
  ('john', true),
  ('peter', true),
  ('james', true),
  ('lenny', true),
  ('carl', true),
  ('homer', true),
  ('moe', true),
  ('sebastian', false);

INSERT INTO games (player_a_1, player_a_2, player_b_1, player_b_2, winning_team) VALUES
  ((SELECT id FROM players WHERE nickname = 'homer'),
   (SELECT id FROM players WHERE nickname = 'moe'),
   (SELECT id FROM players WHERE nickname = 'lenny'),
   (SELECT id FROM players WHERE nickname = 'carl'),
   'A'),

 ((SELECT id FROM players WHERE nickname = 'john'),
   (SELECT id FROM players WHERE nickname = 'peter'),
   (SELECT id FROM players WHERE nickname = 'james'),
   (SELECT id FROM players WHERE nickname = 'sebastian'),
   'B');

