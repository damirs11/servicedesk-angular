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
  cha_per_man_oid DECIMAL(18),
  cha_sta_oid DECIMAL(18),
  cha_imp_oid DECIMAL(18),
  reg_created DATETIME,
  cha_deadline DATETIME,
  cha_actualfinish DATETIME
);

CREATE TABLE itsm_cha_information (
  chi_cha_oid DECIMAL(18),
  chi_information VARCHAR(4000)
);

CREATE TABLE itsm_workorders (
  WOR_OID DECIMAL(18),
  WOR_ID DECIMAL(18),
  REG_CREATED TIMESTAMP,
  WOR_DEADLINE TIMESTAMP,
  REG_MODIFIED TIMESTAMP,
  WOR_ACTUALFINISH TIMESTAMP,
  WOR_DESCRIPTION VARCHAR (80),
  WOR_STA_OID DECIMAL(18),
  WOR_CAT_OID DECIMAL(18),
  WOR_CLO_OID DECIMAL(18),
  WOR_REQUESTOR_PER_OID DECIMAL(18),
  ASS_WORKGROUP DECIMAL(18),
  ASS_PER_TO_OID DECIMAL(18),
  WOR_SER_OID DECIMAL(18),
  WOR_CHA_OID DECIMAL(18)
);

CREATE TABLE itsm_wor_information (
  WOI_WOR_OID DECIMAL(18),
  WOI_INFORMATION VARCHAR(4000)
)

CREATE TABLE itsm_wor_custom_fields (
  WCF_WOR_OID DECIMAL(18),
  WCF_ORG1_OID DECIMAL(18),
  WCF_DURATION1 FLOAT,
  WCF_BOOLEAN2 DECIMAL(1)
)

CREATE TABLE itsm_wor_4k1 (
  WO1_WOR_OID VARCHAR (18),
  WO1_4K1 VARCHAR (4000)
)