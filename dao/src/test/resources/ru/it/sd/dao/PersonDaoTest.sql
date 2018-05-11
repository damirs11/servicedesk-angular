INSERT INTO itsm_organizations (org_oid, org_name1, org_email) VALUES
  (1, 'Хилтон', 'info@hilton.com');
INSERT INTO itsm_organizations (org_oid, org_name1, org_email) VALUES
  (2, 'Плаза', 'hello@plaza.com');

INSERT INTO itsm_workgroups (wog_oid, wog_name, wog_searchcode) VALUES (
  20001, 'Группа поддержки БД', 'БД-DOMINO');
INSERT INTO itsm_workgroups (wog_oid, wog_name, wog_searchcode) VALUES (
  20002, 'TESTWG2', 'БД-DOMINO');
-----Folder------
INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (12345678, 396748, 2, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (12345678, 'FolderTest', 1049);

INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid, per_poo_oid) VALUES
  (1, 1, NULL, 'Менеджер', 'Иван', 'Иванов', 'Иванович', 1, 12345678);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (2, 1, 'petr@sample.com', 'Тех. писатель', 'Петр', 'Петров', 'Петрович', NULL);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (3, 0, NULL, 'Секретарь', 'Ольга', 'Сергеева', 'Петровна', 2);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (4, NULL, NULL, 'Сантехник', 'Марио', 'Сегов', 'Куябович', 2);
INSERT INTO itsm_persons (per_oid, per_name) VALUES
  (5, 'Имя Фамилия Андреевич');
INSERT INTO itsm_persons (per_oid, per_name) VALUES
  (6, 'Имя Фамилия Александрович');
INSERT INTO itsm_persons (per_oid, per_name) VALUES
  (7, 'Имя Фамилия Денисовна');

INSERT INTO itsm_members (mem_oid, mem_wog_oid, mem_per_oid) VALUES
  (1, 20001, 1);
INSERT INTO itsm_members (mem_oid, mem_wog_oid, mem_per_oid) VALUES
  (2, 20001, 2);
INSERT INTO itsm_members (mem_oid, mem_wog_oid, mem_per_oid) VALUES
  (3, 20002, 3);
INSERT INTO itsm_members (mem_oid, mem_wog_oid, mem_per_oid) VALUES
  (4, 20002, 4);