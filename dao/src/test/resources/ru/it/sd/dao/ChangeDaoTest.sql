INSERT INTO itsm_organizations (org_oid, org_name1, org_email) VALUES
  (2, 'Плаза', 'hello@plaza.com');

INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (1, 1, NULL, 'Менеджер', 'Иван', 'Иванов', 'Иванович', 2);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (2, 0, NULL, 'Секретарь', 'Ольга', 'Сергеева', 'Петровна', 2);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid) VALUES
  (4, NULL, NULL, 'Сантехник', 'Марио', 'Сегов', 'Куябович', 2);

INSERT INTO itsm_workgroups (wog_oid, wog_name, wog_searchcode, wog_sta_oid) VALUES (
  20000, 'WG1', 'WG1', 281478224675129);
INSERT INTO itsm_workgroups (wog_oid, wog_name, wog_searchcode, wog_sta_oid, wog_parent) VALUES (
  20001, 'Группа поддержки БД', 'БД-DOMINO', 281478224675129, 20000);

---------------------------------CHANGES-------------------------------------------------------------
INSERT INTO itsm_changes (cha_oid, cha_id, cha_cla_oid, cha_cat_oid, ass_wog_oid) VALUES
  (111222, 112, 3095134296, 3095397034, 20001);
INSERT INTO itsm_changes (cha_oid, cha_id, ass_per_to_oid, cha_requestor_per_oid, cha_per_man_oid) VALUES
  (266633, 11, 2, 2, 2);
INSERT INTO itsm_changes (cha_oid, cha_id, cha_per_man_oid) VALUES
  (331234, 12, 2);
INSERT INTO itsm_changes (cha_oid, cha_id, cha_requestor_per_oid, cha_per_man_oid) VALUES
  (442434, 15, 2, 2);


INSERT INTO itsm_approver_votes (apv_oid, apv_apt_oid, apv_per_oid, apv_approved, apv_reason) VALUES
  (8183, 266633, 4, 1, 'одобряю');
INSERT INTO itsm_approver_votes (apv_oid, apv_apt_oid, apv_per_oid, apv_approved, apv_reason) VALUES
  (84234, 266633, 2, 1, 'все ок');
INSERT INTO itsm_approver_votes (apv_oid, apv_apt_oid, apv_per_oid, apv_approved, apv_reason) VALUES
  (9622, 331234, 2, 1, '100500!');



