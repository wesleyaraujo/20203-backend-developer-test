
INSERT INTO users (email, name, created_at) VALUES
('admin@gmail.com', 'Admin', current_timestamp),
('siva@gmail.com', 'Siva', current_timestamp)
;

INSERT INTO survivors (name, birthdate, gender, last_latitude, last_longitude) VALUES
('Wesley',to_date('1989-08-14','yyyy-MM-dd'), 'M', -5.08921, -42.8016);