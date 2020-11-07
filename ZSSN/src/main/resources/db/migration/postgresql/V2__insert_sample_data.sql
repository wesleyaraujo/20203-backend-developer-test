INSERT INTO survivors (name, birthdate, gender, last_latitude, last_longitude, created_at) VALUES
('Wesley',to_date('1989-08-14','yyyy-MM-dd'), 'M', -5.08921, -42.8016, current_timestamp);

INSERT INTO resources (name, point) VALUES
('Water',4),
('Food',3),
('Medication',2),
('Ammunition',1)
;