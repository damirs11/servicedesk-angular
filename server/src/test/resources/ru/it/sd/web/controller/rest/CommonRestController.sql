INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (3095134329,724041777, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (3095134329, 'Реализация', 1049);

INSERT INTO itsm_organizations (org_oid, org_name1, org_email) VALUES
  (1, 'Хилтон', 'info@hilton.com');
INSERT INTO itsm_organizations (org_oid, org_name1, org_email) VALUES
  (2, 'Плаза', 'hello@plaza.com');

INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (1, 1, NULL, 'Менеджер', 'Иван', 'Иванов', 'Иванович', 1);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (2, 1, 'petr@sample.com', 'Тех. писатель', 'Петр', 'Петров', 'Петрович', NULL);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (3, 0, NULL, 'Секретарь', 'Ольга', 'Сергеева', 'Петровна', 2);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (4, NULL, NULL, 'Сантехник', 'Марио', 'Сегов', 'Куябович', 2);

INSERT INTO itsm_workorders (wor_oid, wor_id, wor_sta_oid, wor_cat_oid, wor_clo_oid, wor_cha_oid) VALUES
  (10001, 1001, 281478237061818, 3095134393, 3095134405, 111222);