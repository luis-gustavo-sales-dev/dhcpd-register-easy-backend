

INSERT INTO tb_device_group (name) VALUES ('Servidor');
INSERT INTO tb_device_group (name) VALUES ('Terceirizado');
INSERT INTO tb_device_group (name) VALUES ('Bolsista');
INSERT INTO tb_device_group (name) VALUES ('Estagiario');
INSERT INTO tb_device_group (name) VALUES ('Temporario');

INSERT INTO tb_ip_range_group (range) VALUES ('10.10.160.0/22');
INSERT INTO tb_ip_range_group (range) VALUES ('10.12.160.0/22');

INSERT INTO tb_ip_range_group (range) VALUES ('10.10.164.0/23');
INSERT INTO tb_ip_range_group (range) VALUES ('10.12.164.0/23');

INSERT INTO tb_ip_range_group (range) VALUES ('10.10.166.0/23');
INSERT INTO tb_ip_range_group (range) VALUES ('10.12.166.0/23');

INSERT INTO tb_ip_range_group (range) VALUES ('10.10.168.0/23');
INSERT INTO tb_ip_range_group (range) VALUES ('10.12.168.0/23');

INSERT INTO tb_ip_range_group (range) VALUES ('10.10.170.0/24');
INSERT INTO tb_ip_range_group (range) VALUES ('10.12.170.0/24');

insert into tb_device_group_iprangegroup (device_user_group_id, iprangegroup_id) values (1,1),(1,2),(2,3),(2,4),(3,5),(3,6),(4,7),(4,8),(5,9),(5,10);

INSERT INTO tb_device_type (id, name) values (1, 'Celular'),(2, 'Notebook'),(3, 'Desktop'),(4, 'Tablet'),(5, 'Access Point'),(6, 'VoIP'),(7, 'Outros');

INSERT INTO tb_device_registers (cpf, mac, group_id, device_type_id) VALUES ('12244264793', '01:42:23:6f:a7:36', 1, 1);
INSERT INTO tb_device_registers (cpf, mac, group_id, device_type_id) VALUES ('12244264793', '02:42:23:6f:a7:37', 1, 1);
INSERT INTO tb_device_registers (cpf, mac, group_id, device_type_id) VALUES ('12244264793', '01:42:23:6f:a7:38', 1, 1);
INSERT INTO tb_device_registers (cpf, mac, group_id, device_type_id) VALUES ('12244264793', '02:42:23:6f:a7:39', 1, 1);
INSERT INTO tb_device_registers (cpf, mac, group_id, device_type_id) VALUES ('12345678900', '03:42:23:6f:a7:40', 2, 2);

