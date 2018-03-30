  -----Folder------
INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (12345678, 396748, 2, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (12345678, 'FolderTest', 1049);


INSERT INTO itsm_organizations (org_oid, org_name1, org_email, org_poo_oid) VALUES
  (1, 'Хилтон', 'info@hilton.com', 12345678);
INSERT INTO itsm_organizations (org_oid, org_name1, org_email) VALUES
  (2, 'Плаза', 'hello@plaza.com');