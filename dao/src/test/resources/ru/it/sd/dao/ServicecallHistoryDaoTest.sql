INSERT INTO itsm_servicecalls (ser_oid, ser_id) VALUES
  (1, 1);
INSERT INTO itsm_servicecalls (ser_oid, ser_id) VALUES
  (2, 2);

INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_acc_oid) VALUES
  (33, 1, NULL, 'Менеджер', 'Иван', 'Иванов', 'Иванович', 330);
INSERT INTO rep_accounts (acc_oid, acc_loginname, acc_showname) VALUES
  (330, 'test', 'Тестович');

INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_acc_oid) VALUES
  (44, 1, NULL, 'Менеджер', 'Foo', 'Bar', 'Baz', 440);
INSERT INTO rep_accounts (acc_oid, acc_loginname, acc_showname) VALUES
  (440, 'Арсений', 'Белый');

INSERT INTO itsm_historylines_servicecall (hsc_oid, hsc_subject, reg_createdby_oid, hsc_created, hsc_newvalue, hsc_ser_oid) VALUES
  (1, 'Перенесен крайний срок', 33, TIMESTAMP '2005-05-13 07:15:31.123456789', '123', 1);
INSERT INTO itsm_historylines_servicecall (hsc_oid, hsc_subject, reg_createdby_oid, hsc_created, hsc_newvalue, hsc_ser_oid) VALUES
  (2, 'Перенесен крайний срок', 44, TIMESTAMP '2005-05-13 07:15:31.123456789', '123', 2);
INSERT INTO itsm_historylines_servicecall (hsc_oid, hsc_subject, reg_createdby_oid, hsc_created, hsc_newvalue, hsc_ser_oid) VALUES
  (3, 'Перенесен крайний срок', 33, TIMESTAMP '2005-05-13 07:15:31.123456789', '123', 1);
