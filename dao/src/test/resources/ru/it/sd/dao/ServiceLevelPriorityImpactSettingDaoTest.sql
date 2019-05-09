INSERT INTO rep_codes (rcd_oid, rcd_subtype, rcd_rcd_oid) VALUES
(101, 887947265, null);
INSERT INTO rep_codes_text (rct_rcd_oid, rct_name, rct_lng_oid) VALUES
(101, 'Условие приоритета', 1049);

INSERT INTO rep_codes (rcd_oid, rcd_subtype, rcd_rcd_oid) VALUES
(201, 889389215, null);
INSERT INTO rep_codes_text (rct_rcd_oid, rct_name, rct_lng_oid) VALUES
(201, 'Приоритет', 1049);

INSERT INTO itsm_priorityimpactsettings (pis_oid, pis_pri_oid, pis_imp_oid, pis_sel_oid) VALUES
  (1, 101, 201, 1);
INSERT INTO itsm_priorityimpactsettings (pis_oid, pis_pri_oid, pis_imp_oid, pis_sel_oid) VALUES
  (2, 101, 201, 1);
INSERT INTO itsm_priorityimpactsettings (pis_oid, pis_pri_oid, pis_imp_oid, pis_sel_oid) VALUES
  (3, 101, 201, 1);
INSERT INTO itsm_priorityimpactsettings (pis_oid, pis_pri_oid, pis_imp_oid, pis_sel_oid) VALUES
  (4, 101, 201, 2);
