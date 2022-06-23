
INSERT INTO tb_device_group (id, name) VALUES (1, 'Servidor');
INSERT INTO tb_device_group (id, name) VALUES (2, 'Terceirizado');
INSERT INTO tb_device_group (id, name) VALUES (3, 'Bolsista');
INSERT INTO tb_device_group (id, name) VALUES (4, 'Estagiário');
INSERT INTO tb_device_group (id, name) VALUES (5, 'Temporário');


INSERT INTO tb_device_registers (cpf, mac, group_id) VALUES ('12244264793', '01:42:23:6f:a7:37', 1);
INSERT INTO tb_device_registers (cpf, mac, group_id) VALUES ('12244264793', '02:42:23:6f:a7:37', 1);
INSERT INTO tb_device_registers (cpf, mac, group_id) VALUES ('12345678900', '03:42:23:6f:a7:37', 2);

