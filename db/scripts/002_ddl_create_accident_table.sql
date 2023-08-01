CREATE TABLE accident (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    text TEXT NOT NULL,
    address VARCHAR NOT NULL,
    accident_type_id int NOT NULL REFERENCES accident_type(id)
);