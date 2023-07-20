CREATE TABLE accident_rule (
   id SERIAL PRIMARY KEY,
   accident_id int NOT NULL REFERENCES accident(id),
   rule_id int NOT NULL REFERENCES rule(id)
)