INSERT INTO rep_codes (rcd_oid, rcd_subtype, rcd_rcd_oid) VALUES
(1, 887947265, null);
INSERT INTO rep_codes_text (rct_rcd_oid, rct_name, rct_lng_oid) VALUES
(1, 'Условие приоритета 1', 1049);

INSERT INTO itsm_prioritydurationsettings (pds_oid, pds_maximumduration, pds_priority, pds_entity) VALUES
  (1, 1.0, 1, 427236);
INSERT INTO itsm_prioritydurationsettings (pds_oid, pds_maximumduration, pds_priority, pds_entity) VALUES
  (2, 1.1, 1, 427239);
INSERT INTO itsm_prioritydurationsettings (pds_oid, pds_maximumduration, pds_priority, pds_entity) VALUES
  (3, 2.0, 1, 427239);