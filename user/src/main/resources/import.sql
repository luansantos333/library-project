INSERT INTO tb_user (id, username, password) VALUES ( '02d3a9de-2620-4f6a-991e-1b306223c584','johndoe@gmail.com', '$2a$12$7jcoqsViP.e3bADq1synQOmQ.2RZGOfKFniWhYtzmEGdKVkoY9Lh2');
INSERT INTO tb_user (id, username, password) VALUES ( 'c460875e-4799-480b-9951-f657964d2cf7','maryhopkins@gmail.com', '$2a$12$q8ukKOCjW5d0kKy8V835M.v8g4OMaQtot1qoDN8ABt9cn19QK3cv2');
INSERT INTO tb_user (id, username, password) VALUES ( '2cd8f6ee-93d9-48e1-a6fd-73362b7b1007', 'jfidoe@gmail.com', '$2a$12$q8ukKOCjW5d0kKy8V835M.v8g4OMaQtot1qoDN8ABt9cn19QK3cv2');

INSERT INTO tb_role (authority, description)VALUES ('ROLE_ADMIN', 'Administrator role for management');
INSERT INTO tb_role (authority, description)VALUES ('ROLE_USER', 'User role for clients');

INSERT INTO tb_user_role (user_id, role_id) VALUES ('02d3a9de-2620-4f6a-991e-1b306223c584', 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES ('c460875e-4799-480b-9951-f657964d2cf7', 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES ('2cd8f6ee-93d9-48e1-a6fd-73362b7b1007', 2);





