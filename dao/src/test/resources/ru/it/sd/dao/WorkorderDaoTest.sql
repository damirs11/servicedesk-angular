INSERT INTO itsm_workorders (wor_oid, wor_id, WOR_STA_OID, WOR_CAT_OID, WOR_CLO_OID, WOR_CHA_OID) VALUES
  (10001, 1001, 281478237061818, 3095134393, 3095134405, 111222);

INSERT INTO itsm_workorders (wor_oid, wor_id, WOR_STA_OID, WOR_CAT_OID) VALUES
  (10002, 1002, 281478237061820, 281492964966928);

INSERT INTO itsm_workorders (wor_oid, wor_id, WOR_STA_OID, WOR_CAT_OID, WOR_DESCRIPTION ) VALUES
  (10003, 1003, 281478237061820, 281492964966928, 'TEST');

INSERT INTO itsm_workorders (wor_oid, wor_id, WOR_STA_OID, WOR_CAT_OID, WOR_DESCRIPTION) VALUES
  (10004, 1004, 281478237061820, 281492964966928, 'TEST');

INSERT INTO itsm_workorders (wor_oid, wor_id, wor_requestor_per_oid) VALUES
  (10005, 1005, 1);

INSERT INTO itsm_workorders (wor_oid, wor_id, wor_requestor_per_oid) VALUES
  (10006, 1006, 2);

INSERT INTO itsm_workorders (wor_oid, wor_id, wor_requestor_per_oid, ASS_PER_TO_OID) VALUES
  (10007, 1007, 1, 2);