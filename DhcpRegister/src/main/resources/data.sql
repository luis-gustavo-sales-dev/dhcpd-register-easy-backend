

INSERT INTO tb_device_group (id, name) VALUES (1, 'Servidor');
INSERT INTO tb_device_group (id, name) VALUES (2, 'Terceirizado');
INSERT INTO tb_device_group (id, name) VALUES (3, 'Bolsista');
INSERT INTO tb_device_group (id, name) VALUES (4, 'Estagiário');
INSERT INTO tb_device_group (id, name) VALUES (5, 'Temporário');

INSERT INTO tb_ip_range_group (range, device_user_group_id) VALUES ('10.10.96.0/32', 1);
INSERT INTO tb_ip_range_group (range, device_user_group_id) VALUES ('10.12.96.0/32', 1);

INSERT INTO tb_device_registers (cpf, mac, group_id) VALUES ('12244264793', '01:42:23:6f:a7:36', 1);
INSERT INTO tb_device_registers (cpf, mac, group_id) VALUES ('12244264793', '02:42:23:6f:a7:37', 1);
INSERT INTO tb_device_registers (cpf, mac, group_id) VALUES ('12345678900', '03:42:23:6f:a7:38', 2);

