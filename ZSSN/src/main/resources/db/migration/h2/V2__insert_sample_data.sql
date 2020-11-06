
INSERT INTO users (email, name, created_at) VALUES
('admin@gmail.com', 'Admin', CURRENT_TIMESTAMP()),
('siva@gmail.com', 'Siva', CURRENT_TIMESTAMP())
;

INSERT INTO survivors (name, birthdate, gender, last_latitude, last_longitude) VALUES
('Wesley',to_date('1989-08-14','yyyy-MM-dd'), 'M', -5.08921, -42.8016);

INSERT INTO resources (name,point) VALUES
('Water',4),
('Food',3),
('Medication',2),
('Ammunition',1)
;
