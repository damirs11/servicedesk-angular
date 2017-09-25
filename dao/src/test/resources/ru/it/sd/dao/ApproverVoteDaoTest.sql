INSERT INTO itsm_organizations (org_oid, org_name1, org_email) VALUES
  (2, 'Плаза', 'hello@plaza.com');

INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (1, 1, NULL, 'Менеджер', 'Иван', 'Иванов', 'Иванович', 2);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (2, 0, NULL, 'Секретарь', 'Ольга', 'Сергеева', 'Петровна', 2);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (4, NULL, NULL, 'Сантехник', 'Марио', 'Сегов', 'Куябович', 2);



INSERT INTO itsm_approver_votes (apv_oid, apv_apt_oid, apv_per_oid, apv_approved, apv_reason) VALUES
  (8183, 266633, 4, 1, 'одобряю');
INSERT INTO itsm_approver_votes (apv_oid, apv_apt_oid, apv_per_oid, apv_approved, apv_reason) VALUES
  (84234, 266633, 2, 1, 'все ок');
INSERT INTO itsm_approver_votes (apv_oid, apv_apt_oid, apv_per_oid, apv_approved, apv_reason) VALUES
  (9622, 331234, 2, 1, '100500!');



