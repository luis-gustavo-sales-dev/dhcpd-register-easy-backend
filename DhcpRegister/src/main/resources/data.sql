

INSERT INTO tb_device_group (name) VALUES ('Servidor');
INSERT INTO tb_device_group (name) VALUES ('Terceirizado');
INSERT INTO tb_device_group (name) VALUES ('Bolsista');
INSERT INTO tb_device_group (name) VALUES ('Estagiário');
INSERT INTO tb_device_group (name) VALUES ('Temporário');

INSERT INTO tb_ip_range_group (range, device_user_group_id) VALUES ('10.10.160.0/22', 1);
INSERT INTO tb_ip_range_group (range, device_user_group_id) VALUES ('10.12.160.0/22', 1);

INSERT INTO tb_ip_range_group (range, device_user_group_id) VALUES ('10.10.164.0/23', 2);
INSERT INTO tb_ip_range_group (range, device_user_group_id) VALUES ('10.12.164.0/23', 2);

INSERT INTO tb_ip_range_group (range, device_user_group_id) VALUES ('10.10.166.0/23', 3);
INSERT INTO tb_ip_range_group (range, device_user_group_id) VALUES ('10.12.166.0/23', 3);

INSERT INTO tb_ip_range_group (range, device_user_group_id) VALUES ('10.10.168.0/23', 4);
INSERT INTO tb_ip_range_group (range, device_user_group_id) VALUES ('10.12.168.0/23', 4);

INSERT INTO tb_ip_range_group (range, device_user_group_id) VALUES ('10.10.170.0/24', 5);
INSERT INTO tb_ip_range_group (range, device_user_group_id) VALUES ('10.12.170.0/24', 5);

INSERT INTO tb_device_registers (cpf, mac, group_id) VALUES ('12244264793', '01:42:23:6f:a7:36', 1);
INSERT INTO tb_device_registers (cpf, mac, group_id) VALUES ('12244264793', '02:42:23:6f:a7:37', 1);
INSERT INTO tb_device_registers (cpf, mac, group_id) VALUES ('12345678900', '03:42:23:6f:a7:38', 2);

