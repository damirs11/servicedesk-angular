INSERT INTO itsm_organizations (org_oid, org_name1, org_email) VALUES
  (1, 'Хилтон', 'info@hilton.com');
INSERT INTO itsm_organizations (org_oid, org_name1, org_email) VALUES
  (2, 'Плаза', 'hello@plaza.com');

INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid, per_acc_oid) VALUES
  (1, 1, NULL, 'Менеджер', 'Иван', 'Иванов', 'Иванович', 1, 3);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (2, 1, 'petr@sample.com', 'Тех. писатель', 'Петр', 'Петров', 'Петрович', NULL);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (3, 0, NULL, 'Секретарь', 'Ольга', 'Сергеева', 'Петровна', 2);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (4, NULL, NULL, 'Сантехник', 'Марио', 'Сегов', 'Куябович', 2);

INSERT INTO rep_accounts (acc_oid, acc_loginname, acc_showname) VALUES
  (1, 'test', 'Тестович');
INSERT INTO rep_accounts (acc_oid, acc_loginname, acc_showname) VALUES
  (2, 'admin', 'Администратор приложения');
INSERT INTO rep_accounts (acc_oid, acc_loginname, acc_showname) VALUES
  (3, 'manager', 'Менеджер заказов');

INSERT INTO rep_roles_per_account (rpa_oid, rpa_acc_oid, rpa_rol_oid) VALUES
  (1, 1, 281494881712586);
INSERT INTO rep_roles_per_account (rpa_oid, rpa_acc_oid, rpa_rol_oid) VALUES
  (2, 1, 281494881711997);
INSERT INTO rep_roles_per_account (rpa_oid, rpa_acc_oid, rpa_rol_oid) VALUES
  (3, 3, 281494881711474);
INSERT INTO rep_roles_per_account (rpa_oid, rpa_acc_oid, rpa_rol_oid) VALUES
  (4, 3, 281494881711997);