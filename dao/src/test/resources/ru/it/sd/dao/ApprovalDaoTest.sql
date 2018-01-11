INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (281478256721931,724041777, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (281478256721931, 'Подготавливается', 1049);

---------------------------------CHANGES-------------------------------------------------------------
INSERT INTO itsm_changes (cha_oid, cha_apt_status, cha_apt_description, cha_apt_deadline, cha_apt_nrofapprovers, cha_apt_nrofapproversapproved, cha_apt_nrofapproversrequired, cha_apt_wog_oid ) VALUES
  (111222, 281478256721931, 'test ssss', null, 5, 2, 4, null);


INSERT INTO itsm_workorders (wor_oid, wor_apt_status, wor_apt_description, wor_apt_deadline, wor_apt_nrofapprovers, wor_apt_nrofapproversapproved, wor_apt_nrofapproversrequired, wor_apt_wog_oid ) VALUES
  (222222, 281478256721931, 'test ssss', null, 5, 2, 4, null);




