---ACCOUNT
INSERT INTO rep_accounts (acc_oid, acc_loginname, acc_showname) VALUES
  (1, 'test', 'Тестович');

----ROLES---
INSERT INTO rep_roles (rol_oid, rol_description) VALUES
    (281494881712586, 'role1');
INSERT INTO rep_roles (rol_oid, rol_description) VALUES
    (281494881711997, 'role2');
INSERT INTO rep_roles (rol_oid, rol_description) VALUES
    (281494881711474, 'role3');


---ROLES PER ACCOUNT
INSERT INTO rep_roles_per_account (rpa_oid, rpa_acc_oid, rpa_rol_oid) VALUES
  (1, 1, 281494881712586);
INSERT INTO rep_roles_per_account (rpa_oid, rpa_acc_oid, rpa_rol_oid) VALUES
  (2, 1, 281494881711997);


-----ENITY ACCESS
INSERT INTO rep_entity_access ( ena_oid, ena_rol_oid, ena_ent_oid) VALUES
   (1, 281494881712586, 724041768);--attr1
INSERT INTO rep_entity_access ( ena_oid, ena_rol_oid, ena_ent_oid) VALUES
   (2, 281494881711997, 563019801);--attr2
INSERT INTO rep_entity_access ( ena_oid, ena_rol_oid, ena_ent_oid) VALUES
   (3, 281494881711474, 563019801);--attr3
INSERT INTO rep_entity_access ( ena_oid, ena_rol_oid, ena_ent_oid) VALUES
   (4, 281494881711997, 724041768);--attr4
INSERT INTO rep_entity_access ( ena_oid, ena_rol_oid, ena_ent_oid) VALUES
   (5, 281494881711474, 724041768);--attr1
INSERT INTO rep_entity_access ( ena_oid, ena_rol_oid, ena_ent_oid) VALUES
   (6, 281494881712586, 724041768);--attr1


----ATTRIBUTE ACCESS
INSERT INTO rep_attribute_access (ata_oid, ata_atr_oid, ata_modify, ata_ena_oid) VALUES
  (1,11, 0, 1);
INSERT INTO rep_attribute_access (ata_oid, ata_atr_oid, ata_modify, ata_ena_oid) VALUES
  (2,111, 1, 2);
INSERT INTO rep_attribute_access (ata_oid, ata_atr_oid, ata_modify, ata_ena_oid) VALUES
  (3,1111, 0, 3);
INSERT INTO rep_attribute_access (ata_oid, ata_atr_oid, ata_modify, ata_ena_oid) VALUES
  (4,11111, 1, 4);
INSERT INTO rep_attribute_access (ata_oid, ata_atr_oid, ata_modify, ata_ena_oid) VALUES
  (5,11, 0, 5);
INSERT INTO rep_attribute_access (ata_oid, ata_atr_oid, ata_modify, ata_ena_oid) VALUES
  (6,11, 0, 6);





