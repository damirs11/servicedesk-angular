INSERT INTO itsm_workgroups (wog_oid, wog_name, wog_searchcode, wog_sta_oid) VALUES (
  20001, 'Группа поддержки БД', 'БД-DOMINO', 281478224675129);
INSERT INTO itsm_workgroups (wog_oid, wog_name, wog_searchcode, wog_sta_oid) VALUES (
  20002, 'Группа поддержки CRM', 'CRM', 281478224675129);
INSERT INTO itsm_workgroups (wog_oid, wog_name, wog_searchcode, wog_sta_oid) VALUES (
  20003, 'Группа поддержки PROJECT', 'MS-PROJECT', 281478224675127);
INSERT INTO itsm_workgroups (wog_oid, wog_name, wog_searchcode, wog_sta_oid) VALUES (
  20004, 'Группа поддержки SD', 'SD', 281478224675127);
INSERT INTO itsm_workgroups (wog_oid, wog_name, wog_searchcode, wog_sta_oid, wog_parent) VALUES (
  200041, 'Группа поддержки SD1', 'SD', 281478224675127, 20004);
INSERT INTO itsm_workgroups (wog_oid, wog_name, wog_searchcode, wog_sta_oid, wog_parent) VALUES (
  200042, 'Группа поддержки SD2', 'SD', 281478224675127, 20004);
INSERT INTO itsm_workgroups (wog_oid, wog_name, wog_searchcode, wog_sta_oid, wog_parent) VALUES (
  200043, 'Группа поддержки SD3', 'SD', 281478224675129, 20004);

INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename) VALUES
  (1, 1, NULL, 'Менеджер', 'Иван', 'Иванов', 'Иванович');
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename) VALUES
  (2, 1, 'petr@sample.com', 'Тех. писатель', 'Петр', 'Петров', 'Петрович');

INSERT INTO itsm_members (mem_oid, mem_wog_oid, mem_per_oid) VALUES
  (1, 20003, 1);
INSERT INTO itsm_members (mem_oid, mem_wog_oid, mem_per_oid) VALUES
  (2, 20002, 1);
INSERT INTO itsm_members (mem_oid, mem_wog_oid, mem_per_oid) VALUES
  (3, 20002, 2);