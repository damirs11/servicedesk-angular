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
