INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (281478224675129,670761017, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (281478224675129, 'Функционирует', 1049);

INSERT INTO rep_codes(rcd_oid, rcd_rcd_oid, rcd_codedisabled) VALUES
(1,null, 0);
INSERT INTO rep_codes_text( rct_rcd_oid, rct_name, rct_lng_oid) VALUES
( 1, 'fparent1', 1049);

INSERT INTO rep_codes(rcd_oid, rcd_rcd_oid, rcd_codedisabled) VALUES
(2,1,0);
INSERT INTO rep_codes_text( rct_rcd_oid, rct_name, rct_lng_oid) VALUES
( 2, 'fchild1', 1049);

INSERT INTO rep_codes(rcd_oid, rcd_rcd_oid, rcd_codedisabled) VALUES
(3,1,0);
INSERT INTO rep_codes_text( rct_rcd_oid, rct_name, rct_lng_oid) VALUES
( 3, 'fchild2', 1049);

INSERT INTO rep_codes(rcd_oid, rcd_rcd_oid, rcd_codedisabled) VALUES
(4,1,0);
INSERT INTO rep_codes_text( rct_rcd_oid, rct_name, rct_lng_oid) VALUES
( 4, 'fchild3', 1049);
INSERT INTO rep_codes(rcd_oid, rcd_rcd_oid, rcd_codedisabled) VALUES
(5,4,0);
INSERT INTO rep_codes_text( rct_rcd_oid, rct_name, rct_lng_oid) VALUES
( 5, 'fchild31', 1049);