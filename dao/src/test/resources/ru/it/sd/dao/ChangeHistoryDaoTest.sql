INSERT INTO itsm_changes (cha_oid, cha_id) VALUES
  (111, 1110);
INSERT INTO itsm_changes (cha_oid, cha_id) VALUES
  (222, 2220);


INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_acc_oid) VALUES
  (33, 1, NULL, 'Менеджер', 'Иван', 'Иванов', 'Иванович', 330);
INSERT INTO rep_accounts (acc_oid, acc_loginname, acc_showname) VALUES
  (330, 'test', 'Тестович');

INSERT INTO itsm_persons (per_oid, per_gender, per_email, per_jobtitle, per_firstname, per_lastname, per_middlename, per_acc_oid) VALUES
  (44, 1, NULL, 'Менеджер', 'Foo', 'Bar', 'Baz', 440);
INSERT INTO rep_accounts (acc_oid, acc_loginname, acc_showname) VALUES
  (440, 'Арсений', 'Белый');


INSERT INTO itsm_historylines_change (hch_oid, hch_cha_oid, hch_subject, reg_created_by_oid, reg_created) VALUES
  (1, 111, 'Перенесен крайний срок', 1, TIMESTAMP '2005-05-13 07:15:31.123456789');
INSERT INTO itsm_historylines_change (hch_oid, hch_cha_oid, hch_subject) VALUES
  (2, 111, 'Изменен статус с "В работе" на "Решено"');
INSERT INTO itsm_historylines_change (hch_oid, hch_cha_oid, hch_subject, reg_created_by_oid) VALUES
  (3, 222, 'Закрытие заявки', 2);