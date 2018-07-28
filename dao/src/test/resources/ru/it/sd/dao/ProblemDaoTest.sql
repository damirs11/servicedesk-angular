INSERT INTO itsm_organizations (org_oid, org_name1, org_email) VALUES (
  2, 'Плаза', 'hello@plaza.com'
);
INSERT INTO rep_accounts(acc_oid) VALUES (2);
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_org_oid, per_acc_oid) VALUES (
  1, 1, NULL, 'Менеджер', 'Иван', 'Иванов', 'Иванович', 2, 2
);
INSERT INTO itsm_configuration_items (CIT_OID, CIT_ID, CIT_SEARCHCODE, CIT_NAME1, CIT_PRICE, CIT_PURCHASEDATE) VALUES (
  202020, 2001, 'CIT №1', 'Some item', 8.50, '2013-08-08 12:00:00'
);
INSERT INTO itsm_problems (pro_oid, pro_id, pro_sta_oid, pro_requestor_per_oid, pro_cit_oid,
  pro_description) VALUES (
  101, 43, null, 1, 202020, 'Проблема проблем'
);