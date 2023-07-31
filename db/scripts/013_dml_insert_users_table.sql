INSERT INTO users(username, enabled, password, authority_id)
VALUES ('admin', true,'$2a$10$kPdebdXas.ZoxJs7tGQanem3F7/4xu11kpRkD3qg85TC6GSMaCDxi',
(SELECT id FROM authorities WHERE authority = 'ROLE_ADMIN'));