-----------------------------------------------------------------------------------
ALTER TABLE itsm_organizations ADD CONSTRAINT pk_itsm_organizations PRIMARY KEY (org_oid);
-----------------------------------------------------------------------------------
ALTER TABLE itsm_persons ADD CONSTRAINT pk_itsm_persons PRIMARY KEY (per_oid);
ALTER TABLE itsm_persons ADD CONSTRAINT fk_itsm_persons_org FOREIGN KEY (per_org_oid) REFERENCES itsm_organizations (org_oid);
ALTER TABLE itsm_persons ADD CONSTRAINT ch_per_gender CHECK (per_gender IN (0, 1));
-----------------------------------------------------------------------------------
