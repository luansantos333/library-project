
INSERT INTO tb_client (name, last_name, cpf, phone, address_id) VALUES ('John', 'Doe', '37120873067', '41894947384', null);
INSERT INTO tb_client (name, last_name, cpf, phone, address_id) VALUES ('Mary', 'Hopkins', '37120873068', '41794947384', null);
INSERT INTO tb_client (name, last_name, cpf, phone, address_id) VALUES ('John', 'Fidoe', '17120873068', '40794947384', null);

INSERT INTO tb_address (address, city, state, zip, country, client_id) VALUES ('13th Street 47 W 13th St', 'New York','NY', 10011, 'USA', 1);
INSERT INTO tb_address (address, city, state, zip, country, client_id) VALUES ('20 Cooper Square', 'New York','NY', 10003, 'USA',2);
INSERT INTO tb_address (address, city, state, zip, country, client_id) VALUES ('2nd Street Dorm', 'New York','NY', 10004, 'USA', 3);

UPDATE tb_client SET address_id = 1 WHERE id = 1;
UPDATE tb_client SET address_id = 2  WHERE id = 2;
UPDATE tb_client SET  address_id =  3 WHERE id = 3;



