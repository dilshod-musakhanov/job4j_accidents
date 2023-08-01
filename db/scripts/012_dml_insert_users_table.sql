INSERT INTO users (username, enabled, password, authority_id)
VALUES ('root', true, '$2a$10$yqTqj6nspbfUcXftnidOuur4uCL8XdyDC.Mv4cpsPk7wFXvqoPPeu',
(SELECT id FROM authorities WHERE authority = 'ROLE_ADMIN'));