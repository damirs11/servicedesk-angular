--Папка----
INSERT INTO rep_codes (rcd_oid, rcd_subtype, rcd_rcd_oid) VALUES
  (1, 396748, null);
INSERT INTO rep_codes_text (rct_rcd_oid, rct_name, rct_lng_oid) VALUES
  (1, 'Папка', 1049);
--Статус--
INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (2,670761017, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (2, 'Статус', 1049);
--Категория--
INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (3,670761017, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (3, 'Категория', 1049);
--Класификация--
INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (4,670761017, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (4, 'Класификация', 1049);
--Код завершения--
INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
  (5,670761017, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
  (5, 'Код завершения', 1049);
--Заявитель
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename ) VALUES
  (1, 1, NULL, 'Менеджер', 'Иван', 'Иванов', 'Иванович');
--Инициатор
INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename ) VALUES
  (2, 1, NULL, 'Менеджер2', 'Иван2', 'Иванов2', 'Иванович2');
--Организация
INSERT INTO itsm_organizations (org_oid, org_name1, org_email) VALUES
  (1, 'Плаза', 'hello@plaza.com');
--Service--
INSERT INTO itsm_services(srv_oid, srv_name) VALUES
  (1, 'Service1');
--SLA--
INSERT INTO itsm_service_level_agreements(sla_oid, sla_srv_oid, sla_name) VALUES
  (1, 1, 'SLA1');
--ЗАявка--
INSERT INTO itsm_servicecalls (ser_oid, ser_poo_oid, ser_sta_oid, ser_cat_oid, ser_cla_oid, ser_clo_oid, ser_requestor_per_oid, ser_caller_per, ser_caller_org) VALUES
  (1, 1, 2, 3, 4, 5, 1, 2, 1);
INSERT INTO itsm_servicecalls (ser_oid, ser_poo_oid, ser_sta_oid, ser_cat_oid, ser_cla_oid, ser_clo_oid, ser_requestor_per_oid, ser_caller_per, ser_caller_org) VALUES
  (2, 1, 2, 3, 4, 5, 1, 2, 1);
INSERT INTO itsm_servicecalls (ser_oid, ser_sla_oid) VALUES
  (3, 1);
INSERT INTO itsm_servicecalls (ser_oid, ser_srv_oid) VALUES
  (4, 1);