INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (281478237061820,854589446, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (281478237061820, 'Открыт', 1049);
INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (281478237061818,854589446, 2, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (281478237061818, 'Закрыт', 1049);


INSERT INTO itsm_workorders (wor_oid, wor_id, wor_sta_oid, wor_cat_oid, wor_clo_oid, wor_cha_oid, ass_workgroup) VALUES
  (10001, 1001, 281478237061818, 3095134393, 3095134405, 111222, 20001);

INSERT INTO itsm_workorders (wor_oid, wor_id, wor_sta_oid, wor_cat_oid, ass_workgroup) VALUES
  (10002, 1002, 281478237061820, 281492964966928, 20001);

INSERT INTO itsm_workorders (wor_oid, wor_id, wor_sta_oid, wor_cat_oid, wor_description ) VALUES
  (10003, 1003, 281478237061820, 281492964966928, 'TEST');

INSERT INTO itsm_workorders (wor_oid, wor_id, wor_sta_oid, wor_cat_oid, wor_description) VALUES
  (10004, 1004, 281478237061820, 281492964966928, 'TEST');

INSERT INTO itsm_workorders (wor_oid, wor_id, wor_requestor_per_oid) VALUES
  (10005, 1005, 1);

INSERT INTO itsm_workorders (wor_oid, wor_id, wor_requestor_per_oid) VALUES
  (10006, 1006, 2);

INSERT INTO itsm_workorders (wor_oid, wor_id, wor_requestor_per_oid, ass_per_to_oid) VALUES
  (10007, 1007, 1, 2);
