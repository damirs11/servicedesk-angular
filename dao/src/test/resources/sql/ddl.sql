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
  cha_id DECIMAL(18)
);