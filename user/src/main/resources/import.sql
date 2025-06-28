INSERT INTO tb_user (id, username, password) VALUES ('a0cc9eec-bb62-421b-979b-3139801bb102','josezinho33@gmail.com', '$2a$12$7jcoqsViP.e3bADq1synQOmQ.2RZGOfKFniWhYtzmEGdKVkoY9Lh2');
INSERT INTO tb_user (id, username, password) VALUES ('415d99e0-e5b1-4271-be99-74f6160c9e47','pedrinho321@gmail.com', '$2a$12$q8ukKOCjW5d0kKy8V835M.v8g4OMaQtot1qoDN8ABt9cn19QK3cv2');
INSERT INTO tb_user (id, username, password) VALUES ('bfac4748-d96e-4edd-a202-b9087b7f061a', 'mariazinha@gmail.com', '$2a$12$q8ukKOCjW5d0kKy8V835M.v8g4OMaQtot1qoDN8ABt9cn19QK3cv2');

INSERT INTO tb_role (authority, description)VALUES ('ROLE_ADMIN', 'Administrator role for management');
INSERT INTO tb_role (authority, description)VALUES ('ROLE_USER', 'User role for clients');

INSERT INTO tb_user_role (user_id, role_id) VALUES ('a0cc9eec-bb62-421b-979b-3139801bb102', 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES ('415d99e0-e5b1-4271-be99-74f6160c9e47', 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES ('bfac4748-d96e-4edd-a202-b9087b7f061a',2);





