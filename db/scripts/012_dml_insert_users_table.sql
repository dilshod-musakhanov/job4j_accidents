INSERT INTO users (username, enabled, password, authority_id)
VALUES ('root', true, '$2a$10$UgTHi1zyjKOsXESMT.c2SuUbDGYXGjNsfX1uen0BPhF8D2a5jZQn6',
(SELECT id FROM authorities WHERE authority = 'ROLE_ADMIN'));