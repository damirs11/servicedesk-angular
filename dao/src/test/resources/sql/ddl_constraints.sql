-----------------------------------------------------------------------------------
ALTER TABLE itsm_organizations ADD CONSTRAINT pk_organizations PRIMARY KEY (org_oid);
-----------------------------------------------------------------------------------
ALTER TABLE itsm_persons ADD CONSTRAINT pk_persons PRIMARY KEY (per_oid);
ALTER TABLE itsm_persons ADD CONSTRAINT fk_persons_org FOREIGN KEY (per_org_oid) REFERENCES itsm_organizations (org_oid);
ALTER TABLE itsm_persons ADD CONSTRAINT ch_persons_per_gender CHECK (per_gender IN (0, 1));
-----------------------------------------------------------------------------------
ALTER TABLE rep_accounts ADD CONSTRAINT pk_rep_accounts PRIMARY KEY (acc_oid);
-----------------------------------------------------------------------------------
ALTER TABLE rep_roles ADD CONSTRAINT pk_rep_roles PRIMARY KEY (rol_oid);
ALTER TABLE rep_roles ADD CONSTRAINT ch_rep_roles_update CHECK (rol_updateallallowed IN (0, 1));
-----------------------------------------------------------------------------------
ALTER TABLE rep_roles_per_account ADD CONSTRAINT pk_accounts_roles PRIMARY KEY (rpa_oid);
ALTER TABLE rep_roles_per_account ADD CONSTRAINT fk_accounts_roles_acc FOREIGN KEY (rpa_acc_oid) REFERENCES rep_accounts (acc_oid);
ALTER TABLE rep_roles_per_account ADD CONSTRAINT fk_accounts_roles_rol FOREIGN KEY (rpa_rol_oid) REFERENCES rep_roles (rol_oid);
-----------------------------------------------------------------------------------
ALTER TABLE itsm_changes ADD CONSTRAINT pk_changes PRIMARY KEY (cha_oid);
ALTER TABLE itsm_changes ADD CONSTRAINT fk_changes_req_per FOREIGN KEY (cha_requestor_per_oid) REFERENCES itsm_persons (per_oid);
ALTER TABLE itsm_changes ADD CONSTRAINT fk_changes_man_per FOREIGN KEY (cha_per_man_oid) REFERENCES itsm_persons (per_oid);
-----------------------------------------------------------------------------------
ALTER TABLE itsm_cha_information ADD CONSTRAINT pk_information PRIMARY KEY (chi_cha_oid);
ALTER TABLE itsm_cha_information ADD CONSTRAINT fk_information_changes FOREIGN KEY (chi_cha_oid) REFERENCES itsm_changes (cha_oid);
-----------------------------------------------------------------------------------
ALTER TABLE itsm_workorders ADD CONSTRAINT pk_workorders PRIMARY KEY (wor_oid);
-----------------------------------------------------------------------------------
ALTER TABLE itsm_workgroups ADD CONSTRAINT pk_workgroups PRIMARY KEY (wog_oid);
ALTER TABLE itsm_workgroups ADD CONSTRAINT fk_workgroups_parent FOREIGN KEY (wog_parent) REFERENCES itsm_workgroups(wog_oid);
-----------------------------------------------------------------------------------
ALTER TABLE itsm_configuration_items ADD CONSTRAINT pk_configuration_items PRIMARY KEY (cit_oid);
-----------------------------------------------------------------------------------
ALTER TABLE itsm_members ADD CONSTRAINT pk_members PRIMARY KEY (mem_oid);
ALTER TABLE itsm_members ADD CONSTRAINT fk_members_wog FOREIGN KEY (mem_wog_oid) REFERENCES itsm_workgroups(wog_oid);
ALTER TABLE itsm_members ADD CONSTRAINT fk_members_pre FOREIGN KEY (mem_per_oid) REFERENCES itsm_persons(per_oid);
-----------------------------------------------------------------------------------
ALTER TABLE itsm_historylines_change ADD CONSTRAINT pk_historylines_changes PRIMARY KEY (hch_oid);
-----------------------------------------------------------------------------------
ALTER TABLE itsm_approver_votes ADD CONSTRAINT pk_votes PRIMARY KEY (apv_oid);
ALTER TABLE itsm_approver_votes ADD CONSTRAINT fk_votes_per FOREIGN KEY (apv_per_oid) REFERENCES itsm_persons(per_oid);
ALTER TABLE itsm_approver_votes ADD CONSTRAINT ch_votes_approved CHECK (apv_approved IN (0, 1));
-----------------------------------------------------------------------------------
ALTER TABLE rep_entity_access ADD CONSTRAINT pk_access PRIMARY KEY (ena_oid);
ALTER TABLE rep_entity_access ADD CONSTRAINT fk_access_roles FOREIGN KEY (ena_rol_oid) REFERENCES rep_roles(rol_oid);
-----------------------------------------------------------------------------------
ALTER TABLE rep_attachments ADD CONSTRAINT pk_attachment PRIMARY KEY (ahs_oid);
-----------------------------------------------------------------------------------
ALTER TABLE itsm_codes ADD CONSTRAINT pk_itsm_codes PRIMARY KEY (cod_oid);
ALTER TABLE itsm_codes_locale ADD CONSTRAINT fk_itsm_codes FOREIGN KEY (cdl_cod_oid) REFERENCES itsm_codes (cod_oid);
-----------------------------------------------------------------------------------
ALTER TABLE rep_codes ADD CONSTRAINT pk_rep_codes PRIMARY KEY (rcd_oid);
ALTER TABLE rep_codes_text ADD CONSTRAINT fk_rep_codes FOREIGN KEY (rct_rcd_oid) REFERENCES rep_codes (rcd_oid);
-----------------------------------------------------------------------------------
ALTER TABLE itsm_service_relations ADD CONSTRAINT pk_ser PRIMARY KEY (sre_oid);
-------------------------------------------------------------------------------------
ALTER TABLE rep_attribute_access ADD CONSTRAINT pk_attribute_access PRIMARY KEY (ata_oid);
