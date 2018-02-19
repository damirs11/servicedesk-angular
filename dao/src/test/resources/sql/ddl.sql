CREATE TABLE itsm_persons (
  per_oid DECIMAL(18),
  per_gender DECIMAL(1),
  per_email VARCHAR(255),
  per_jobtitle VARCHAR(50),
  per_firstname VARCHAR(50),
  per_lastname VARCHAR(50),
  per_middlename VARCHAR(50),
  per_org_oid DECIMAL(18),
  per_acc_oid DECIMAL(18)
);

CREATE TABLE itsm_organizations (
  org_oid DECIMAL(18),
  org_name1 VARCHAR(255),
  org_email VARCHAR(50)
);

CREATE TABLE rep_accounts (
  acc_oid DECIMAL(18),
  acc_loginname VARCHAR(40),
  acc_showname VARCHAR(50)
);

CREATE TABLE rep_roles_per_account (
  rpa_oid DECIMAL(18),
  rpa_acc_oid DECIMAL(18),
  rpa_rol_oid DECIMAL(18)
);

CREATE TABLE itsm_changes (
  cha_oid DECIMAL(18),
  cha_id DECIMAL(18),
  cha_description VARCHAR(80),
  cha_requestor_per_oid DECIMAL(18),
  ass_per_to_oid DECIMAL(18),
  ass_wog_oid DECIMAL(18),
  cha_per_man_oid DECIMAL(18),
  cha_sta_oid DECIMAL(18),
  cha_imp_oid DECIMAL(18),
  cha_cat_oid DECIMAL(18),
  cha_cla_oid DECIMAL(18),
  reg_created DATETIME,
  cha_deadline DATETIME,
  cha_actualfinish DATETIME,
  cha_apt_status DECIMAL(18),
  cha_apt_description VARCHAR(80),
  cha_apt_deadline DATETIME,
  cha_apt_nrofapprovers DECIMAL(18),
  cha_apt_nrofapproversapproved DECIMAL(18),
  cha_apt_nrofapproversrequired DECIMAL(18),
  cha_apt_wog_oid DECIMAL(18),
  cha_closurecode DECIMAL(18),
  cha_poo_oid DECIMAL(18),
  cha_initiator_per_oid DECIMAL(18)
);

CREATE TABLE itsm_cha_information (
  chi_cha_oid DECIMAL(18),
  chi_information VARCHAR(4000)
);

CREATE TABLE itsm_workorders (
  wor_oid DECIMAL(18),
  wor_id DECIMAL(18),
  reg_created TIMESTAMP,
  wor_deadline TIMESTAMP,
  reg_modified TIMESTAMP,
  wor_actualfinish TIMESTAMP,
  wor_description VARCHAR (80),
  wor_sta_oid DECIMAL(18),
  wor_cat_oid DECIMAL(18),
  wor_clo_oid DECIMAL(18),
  wor_requestor_per_oid DECIMAL(18),
  ass_workgroup DECIMAL(18),
  ass_per_to_oid DECIMAL(18),
  wor_ser_oid DECIMAL(18),
  wor_cha_oid DECIMAL(18),
  wor_apt_status DECIMAL(18),
  wor_apt_description VARCHAR(80),
  wor_apt_deadline DATETIME,
  wor_apt_nrofapprovers DECIMAL(18),
  wor_apt_nrofapproversapproved DECIMAL(18),
  wor_apt_nrofapproversrequired DECIMAL(18),
  wor_apt_wog_oid DECIMAL(18),
  wor_initiator_per_oid DECIMAL(18)
);

CREATE TABLE itsm_wor_information (
  woi_wor_oid DECIMAL(18),
  woi_information VARCHAR(4000)
);

CREATE TABLE itsm_wor_custom_fields (
  wcf_wor_oid DECIMAL(18),
  wcf_org1_oid DECIMAL(18),
  wcf_duration1 FLOAT,
  wcf_boolean2 DECIMAL(1)
);

CREATE TABLE itsm_wor_4k1 (
  wo1_wor_oid VARCHAR (18),
  wo1_4k1 VARCHAR (4000)
);

CREATE TABLE itsm_configuration_items (
  cit_oid DECIMAL(18),
  cit_id DECIMAL(18),
  cit_searchcode VARCHAR (128),
  cit_name1 VARCHAR (128),
  cit_name2 VARCHAR (128),
  cit_ordernr VARCHAR (128),
  cit_serialnumber VARCHAR (128),
  cit_remark VARCHAR (128),
  cit_ipaddress VARCHAR (128),
  cit_price DECIMAL (12,2),
  cit_attachment_exists DECIMAL (1),
  cit_sta_oid DECIMAL(18),
  cit_cat_oid DECIMAL(18),
  cit_loc_oid DECIMAL(18),
  cit_poo_oid DECIMAL(18),
  cit_admin_per_oid DECIMAL(18),
  cit_owner_per_oid DECIMAL(18),
  cit_owner_org_oid DECIMAL(18),
  ccf_org1_oid DECIMAL(18),
  cit_org_oid DECIMAL(18),
  cit_purchasedate TIMESTAMP,
  cit_warrantydate TIMESTAMP,
  cit_bra_oid DECIMAL (18)
);

CREATE TABLE itsm_cit_custom_fields (
  ccf_cit_oid DECIMAL (18),
  ccf_citext1 VARCHAR (128),
  ccf_cidate1 TIMESTAMP,
  ccf_org1_oid DECIMAL (18),
  ccf_cishorttext1 VARCHAR (128)
);

CREATE TABLE itsm_codes (
  cod_oid DECIMAL (18),
  cod_subtype DECIMAL(18),
  cod_ordering DECIMAL(9),
  cod_disabled DECIMAL(1)
);

CREATE TABLE itsm_codes_locale (
  cdl_cod_oid DECIMAL (18),
  cdl_name VARCHAR (128),
  cdl_lng_oid DECIMAL (18)
);

CREATE TABLE rep_codes (
  rcd_oid DECIMAL (18),
  rcd_subtype DECIMAL(18),
  rcd_ordering DECIMAL(9),
  rcd_codedisabled DECIMAL(1),
  rcd_rcd_oid DECIMAL(18)
);

CREATE TABLE rep_codes_text (
  rct_rcd_oid DECIMAL (18),
  rct_name VARCHAR (128),
  rct_lng_oid DECIMAL (18)
);

CREATE TABLE itsm_workgroups (
  wog_oid DECIMAL (18),
  wog_name VARCHAR (128),
  wog_searchcode VARCHAR (128),
  wog_sta_oid DECIMAL (18),
  wog_parent DECIMAL (18)
);

CREATE TABLE itsm_members (
  mem_oid DECIMAL (18),
  mem_wog_oid DECIMAL (18),
  mem_per_oid DECIMAL (18)
);

CREATE TABLE itsm_historylines_change (
  hch_oid DECIMAL (18),
  reg_created TIMESTAMP,
  reg_created_by_oid DECIMAL (18),
  hch_subject VARCHAR (255),
  hch_cha_oid DECIMAL (18),
  hch_newvalue VARCHAR (128),
  hch_valueatr_oid DECIMAL (18)
);

CREATE TABLE itsm_approver_votes (
  apv_oid DECIMAL (18),
  apv_apt_oid DECIMAL (18),
  apv_approved DECIMAL (1),
  apv_per_oid DECIMAL (18),
  apv_reason VARCHAR (255)
);

CREATE TABLE rep_attachments (
  ahs_oid DECIMAL (18),
  ahs_ent_oid DECIMAL(18),
  ahs_att_oid DECIMAL(18),
  ahs_basename VARCHAR(128),
  ahs_filename VARCHAR(128)
);

CREATE TABLE itsm_service_relations (
    sre_oid DECIMAL(18),
    sre_revrty_oid DECIMAL(18),
    sre_ent_oid DECIMAL(18),
    sre_rty_oid DECIMAL(18),
    sre_inc_oid DECIMAL(18),
    sre_pro_oid DECIMAL(18),
    sre_ser_oid DECIMAL(18),
    sre_cha_oid DECIMAL(18)
);

CREATE TABLE rep_roles (
  rol_oid DECIMAL (18),
  rol_description VARCHAR (255),
  rol_updateallallowed DECIMAL (1)
);

CREATE TABLE ifc_entities (
  ent_oid DECIMAL (18),
  ent_name VARCHAR (50)
);

CREATE TABLE rep_entity_access (
  ena_oid DECIMAL (18),
  ena_rol_oid DECIMAL (18),
  ena_ent_oid DECIMAL (18),
  ena_view DECIMAL (1),
  ena_viewasnuser DECIMAL (1),
  ena_viewasnwg DECIMAL (1),
  ena_modify DECIMAL (1),
  ena_modifyasnuser DECIMAL (1),
  ena_modifyasnwg DECIMAL (1),
  ena_status_from_oid DECIMAL (18),
  ena_status_to_oid DECIMAL (18),
  ena_new DECIMAL (1),
  ena_delete DECIMAL (1),
  ena_historyview DECIMAL (1),
  ena_historynew DECIMAL (1),
  ena_historymodify DECIMAL (1),
  ena_historymodifycreateduser DECIMAL (1),
  ena_historymodifycreatedwg DECIMAL (1),
  ena_historydelete DECIMAL (1),
  ena_historydeletecreatedwg DECIMAL (1),
  ena_historydeletecreateduser DECIMAL (1),
  ena_cod_oid DECIMAL (18),
  ena_lockseq DECIMAL (9),
  ena_tem_oid DECIMAL (18),
  ena_modifytemplate DECIMAL (1)
);

CREATE TABLE rep_attribute_access (
  ata_oid DECIMAL(18),
  ata_atr_oid DECIMAL(18),
  ata_modify DECIMAL(1),
  ata_ena_oid DECIMAL(18)
);