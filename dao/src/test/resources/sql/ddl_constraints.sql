-----------------------------------------------------------------------------------
ALTER TABLE itsm_organizations ADD CONSTRAINT pk_organizations PRIMARY KEY (org_oid);
-----------------------------------------------------------------------------------
ALTER TABLE itsm_persons ADD CONSTRAINT pk_persons PRIMARY KEY (per_oid);
ALTER TABLE itsm_persons ADD CONSTRAINT fk_persons_org FOREIGN KEY (per_org_oid) REFERENCES itsm_organizations (org_oid);
ALTER TABLE itsm_persons ADD CONSTRAINT ch_persons_per_gender CHECK (per_gender IN (0, 1));
-----------------------------------------------------------------------------------
ALTER TABLE rep_accounts ADD CONSTRAINT pk_rep_accounts PRIMARY KEY (acc_oid);
-----------------------------------------------------------------------------------
ALTER TABLE rep_roles_per_account ADD CONSTRAINT pk_accounts_roles PRIMARY KEY (rpa_oid);
ALTER TABLE rep_roles_per_account ADD CONSTRAINT fk_accounts_roles_acc FOREIGN KEY (rpa_acc_oid) REFERENCES rep_accounts (acc_oid);
-----------------------------------------------------------------------------------
ALTER TABLE itsm_changes ADD CONSTRAINT pk_changes PRIMARY KEY (cha_oid);
-----------------------------------------------------------------------------------