INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (281478224675129,77401, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (281478224675129, 'Вызвано', 1049);

---------------------------------CHANGES-------------------------------------------------------------
INSERT INTO itsm_changes (cha_oid, cha_id) VALUES
  (331234, 12);

INSERT INTO itsm_service_relations (sre_oid, sre_revrty_oid, sre_ent_oid, sre_rty_oid, sre_inc_oid, sre_pro_oid, sre_ser_oid, sre_cha_oid) VALUES
  (12345, 281478224675129, 724041768, null, null, null, 1234512345, 331234);






