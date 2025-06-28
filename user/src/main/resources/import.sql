INSERT INTO tb_user (username, password) VALUES ('josezinho33@gmail.com', '$2a$12$7jcoqsViP.e3bADq1synQOmQ.2RZGOfKFniWhYtzmEGdKVkoY9Lh2');
INSERT INTO tb_user (username, password) VALUES ('pedrinho321@gmail.com', '$2a$12$q8ukKOCjW5d0kKy8V835M.v8g4OMaQtot1qoDN8ABt9cn19QK3cv2');
INSERT INTO tb_user (username, password) VALUES ('mariazinha@gmail.com', '$2a$12$q8ukKOCjW5d0kKy8V835M.v8g4OMaQtot1qoDN8ABt9cn19QK3cv2');

INSERT INTO tb_role (authority, description)VALUES ('ROLE_ADMIN', 'Administrator role for management');
INSERT INTO tb_role (authority, description)VALUES ('ROLE_USER', 'User role for clients');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 2);





