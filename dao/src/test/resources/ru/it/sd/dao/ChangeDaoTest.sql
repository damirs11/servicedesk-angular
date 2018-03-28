INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (281478224675129,670761017, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (281478224675129, 'Функционирует', 1049);
-----Category----------------
INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (3095397040,673513486, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (3095397040, 'Стандартное изменение', 1049);
-----Classifications----------------
INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (3095134296,165879, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (3095134296, 'Инцидент\проблема', 1049);
----- System -----
INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (11111111,165944, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (11111111, 'КИС АХД', 1049);
-----Folders-----
-- null
--   ├ 2
--   ├ 3
--   │   ├ 4
--   │   └ (5)
--   │      └ 6
--   └ 7
--       ├ (8)
--       └ 9
INSERT INTO rep_codes (rcd_oid, rcd_subtype, rcd_rcd_oid) VALUES
  (281485277602002, 396748, null);
INSERT INTO rep_codes_text (rct_rcd_oid, rct_name, rct_lng_oid) VALUES
  (281485277602002, 'СКАРТЕЛ-НСК', 1049);
INSERT INTO rep_codes (rcd_oid, rcd_subtype, rcd_rcd_oid) VALUES
  (281485277602003, 396748, null);
INSERT INTO rep_codes_text (rct_rcd_oid, rct_name, rct_lng_oid) VALUES
  (281485277602003, 'СКАРТЕЛ-УФА', 1049);
INSERT INTO rep_codes (rcd_oid, rcd_subtype, rcd_rcd_oid) VALUES
  (281485277602004, 396748, 281485277602003);
INSERT INTO rep_codes_text (rct_rcd_oid, rct_name, rct_lng_oid) VALUES
  (281485277602004, 'СКАРТЕЛ-СОЧИ', 1049);
INSERT INTO rep_codes (rcd_oid, rcd_subtype, rcd_rcd_oid) VALUES
  (281485277602005, 396748, 281485277602003);
INSERT INTO rep_codes_text (rct_rcd_oid, rct_name, rct_lng_oid) VALUES
  (281485277602005, 'Мирон', 1049);
INSERT INTO rep_codes (rcd_oid, rcd_subtype, rcd_rcd_oid) VALUES
  (281485277602006, 396748, 281485277602005);
INSERT INTO rep_codes_text (rct_rcd_oid, rct_name, rct_lng_oid) VALUES
  (281485277602006, 'Гелиос', 1049);
INSERT INTO rep_codes (rcd_oid, rcd_subtype, rcd_rcd_oid) VALUES
  (281485277602007, 396748, null);
INSERT INTO rep_codes_text (rct_rcd_oid, rct_name, rct_lng_oid) VALUES
  (281485277602007, 'Укатей', 1049);
INSERT INTO rep_codes (rcd_oid, rcd_subtype, rcd_rcd_oid) VALUES
  (281485277602008, 396748, 281485277602007);
INSERT INTO rep_codes_text (rct_rcd_oid, rct_name, rct_lng_oid) VALUES
  (281485277602008, 'Минога', 1049);
INSERT INTO rep_codes (rcd_oid, rcd_subtype, rcd_rcd_oid) VALUES
  (281485277602009, 396748, 281485277602007);
INSERT INTO rep_codes_text (rct_rcd_oid, rct_name, rct_lng_oid) VALUES
  (281485277602009, 'Трезера', 1049);


INSERT INTO itsm_organizations (org_oid, org_name1, org_email) VALUES
  (2, 'Плаза', 'hello@plaza.com');

INSERT INTO rep_accounts(acc_oid) VALUES (1);
INSERT INTO rep_accounts(acc_oid) VALUES (2);
INSERT INTO rep_accounts(acc_oid) VALUES (3);
INSERT INTO rep_accounts(acc_oid) VALUES (4);

INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid, per_acc_oid) VALUES
  (1, 1, NULL, 'Менеджер', 'Иван', 'Иванов', 'Иванович', 2, 2);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid, per_acc_oid) VALUES
  (2, 0, NULL, 'Секретарь', 'Ольга', 'Сергеева', 'Петровна', 2, 3);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid, per_acc_oid) VALUES
  (4, NULL, NULL, 'Сантехник', 'Марио', 'Сегов', 'Куябович', 2, 4);

INSERT INTO itsm_workgroups (wog_oid, wog_name, wog_searchcode, wog_sta_oid) VALUES (
  20000, 'WG1', 'WG1', 281478224675129);
INSERT INTO itsm_workgroups (wog_oid, wog_name, wog_searchcode, wog_sta_oid, wog_parent) VALUES (
  20001, 'Группа поддержки БД', 'БД-DOMINO', 281478224675129, 20000);

---------------------------------CHANGE CUSTOMFIELDS-------------------------------------------------------------
INSERT INTO itsm_cha_custom_fields (ccu_cha_oid, ccu_changecode1) VALUES (
  111222, 11111111
);

INSERT INTO ITSM_CONFIGURATION_ITEMS (CIT_OID, CIT_ID, CIT_SEARCHCODE, CIT_NAME1, CIT_PRICE, CIT_PURCHASEDATE) VALUES (
  202020, 2001, 'CIT №1', 'Some item', 8.50, '2013-08-08 12:00:00'
);
---------------------------------CHANGES-------------------------------------------------------------
INSERT INTO itsm_changes (cha_oid, cha_id, cha_cla_oid, cha_cat_oid, ass_wog_oid, cha_actualstart, cha_planduration) VALUES
  (111222, 112, 3095134296, 3095397040, 20001, '2015-03-31', 1.5);
INSERT INTO itsm_changes (cha_oid, cha_id, ass_per_to_oid, cha_requestor_per_oid, cha_per_man_oid) VALUES
  (266633, 11, 2, 2, 2);
INSERT INTO itsm_changes (cha_oid, cha_id, cha_per_man_oid) VALUES
  (331234, 12, 2);
INSERT INTO itsm_changes (cha_oid, cha_id, cha_requestor_per_oid, cha_per_man_oid) VALUES
  (442434, 15, 2, 2);
INSERT INTO itsm_changes (cha_oid, cha_cit_oid) VALUES
  (101010, 202020);
INSERT INTO itsm_changes (cha_oid, cha_id, cha_requestor_per_oid, cha_per_man_oid, cha_closurecode) VALUES
  (523344, 17, 2, 2, 281485277602006);
INSERT INTO itsm_changes (cha_oid, cha_id, cha_requestor_per_oid, cha_per_man_oid, cha_closurecode) VALUES
  (623344, 18, 2, 2, 281485277602002);
INSERT INTO itsm_changes (cha_oid, cha_id, cha_requestor_per_oid, cha_per_man_oid, cha_closurecode) VALUES
  (723344, 19, 2, 2, 281485277602007);
INSERT INTO itsm_changes (cha_oid, cha_id, cha_requestor_per_oid, cha_per_man_oid, cha_closurecode) VALUES
  (823344, 20, 2, 2, 281485277602005);

INSERT INTO itsm_approver_votes (apv_oid, apv_apt_oid, apv_per_oid, apv_approved, apv_reason) VALUES
  (8183, 266633, 4, 1, 'одобряю');
INSERT INTO itsm_approver_votes (apv_oid, apv_apt_oid, apv_per_oid, apv_approved, apv_reason) VALUES
  (84234, 266633, 2, 1, 'все ок');
INSERT INTO itsm_approver_votes (apv_oid, apv_apt_oid, apv_per_oid, apv_approved, apv_reason) VALUES
  (9622, 331234, 2, 1, '100500!');

-----Roles-----
INSERT INTO rep_roles (rol_oid, rol_description) VALUES
  (281494881712586, 'role1');
INSERT INTO rep_roles (rol_oid, rol_description) VALUES
  (281494881711997, 'role2');
INSERT INTO rep_roles (rol_oid, rol_description) VALUES
  (281494881711474, 'role3');

INSERT INTO rep_roles_per_account(rpa_oid, rpa_acc_oid, rpa_rol_oid) VALUES
  (1, 1, 281494881712586);
INSERT INTO rep_roles_per_account(rpa_oid, rpa_acc_oid, rpa_rol_oid) VALUES
  (2, 1, 281494881711997);
INSERT INTO rep_roles_per_account(rpa_oid, rpa_acc_oid, rpa_rol_oid) VALUES
  (3, 1, 281494881711474);
INSERT INTO rep_roles_per_account(rpa_oid, rpa_acc_oid, rpa_rol_oid) VALUES
  (4, 2, 281494881712586);
INSERT INTO rep_roles_per_account(rpa_oid, rpa_acc_oid, rpa_rol_oid) VALUES
  (5, 3, 281494881711997);
INSERT INTO rep_roles_per_account(rpa_oid, rpa_acc_oid, rpa_rol_oid) VALUES
  (6, 4, 281494881711474);
-----Grants-----
INSERT INTO rep_entity_access (ena_oid, ena_rol_oid, ena_ent_oid, ena_view, ena_cod_oid) VALUES
  (21434001, 281494881712586, 724041768, 1, null);
INSERT INTO rep_entity_access (ena_oid, ena_rol_oid, ena_ent_oid, ena_view, ena_cod_oid) VALUES
  (21434002, 281494881711997, 724041768, 1, 281485277602007);
INSERT INTO rep_entity_access (ena_oid, ena_rol_oid, ena_ent_oid, ena_view, ena_cod_oid) VALUES
  (21434003, 281494881711997, 724041768, 1, null);
INSERT INTO rep_entity_access (ena_oid, ena_rol_oid, ena_ent_oid, ena_view, ena_cod_oid) VALUES
  (21434004, 281494881711474, 724041768, 1, 281485277602004);



