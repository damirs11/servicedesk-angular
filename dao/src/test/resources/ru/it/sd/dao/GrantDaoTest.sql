INSERT INTO rep_accounts(acc_oid) VALUES
(1);

INSERT INTO rep_roles (rol_oid, rol_description, rol_updateallallowed) VALUES
  (281494881712586, 'role1', 1);
INSERT INTO rep_roles (rol_oid, rol_description, rol_updateallallowed) VALUES
  (281494881711997, 'role2', 1);
INSERT INTO rep_roles (rol_oid, rol_description, rol_updateallallowed) VALUES
  (281494881711474, 'role3', 1);

INSERT INTO rep_entity_access (
  ena_oid, ena_rol_oid, ena_ent_oid,
  ena_view, ena_viewasnuser, ena_viewasnwg,
  ena_modify, ena_modifyasnuser, ena_modifyasnwg,
  ena_status_from_oid, ena_status_to_oid,
  ena_new, ena_delete,
  ena_historyview, ena_historynew,
  ena_historymodify, ena_historymodifycreateduser, ena_historymodifycreatedwg,
  ena_historydelete, ena_historydeletecreatedwg, ena_historydeletecreateduser) VALUES
  (1, 281494881712586, 724041768,
  1, NULL, NULL,
  1, NULL, NULL,
  NULL, NULL,
  NULL, NULL,
  NULL, NULL,
  NULL, NULL, NULL,
  NULL, NULL, NULL);
INSERT INTO rep_entity_access (
  ena_oid, ena_rol_oid, ena_ent_oid,
  ena_view, ena_viewasnuser, ena_viewasnwg,
  ena_modify, ena_modifyasnuser, ena_modifyasnwg,
  ena_status_from_oid, ena_status_to_oid,
  ena_new, ena_delete,
  ena_historyview, ena_historynew,
  ena_historymodify, ena_historymodifycreateduser, ena_historymodifycreatedwg,
  ena_historydelete, ena_historydeletecreatedwg, ena_historydeletecreateduser) VALUES
  (2, 281494881712586, 724041768,
      1, NULL, NULL,
      1, 1, NULL,
      NULL, NULL,
    1, 1,
    NULL, NULL,
    NULL, NULL, NULL,
    NULL, NULL, NULL);
INSERT INTO rep_entity_access (
  ena_oid, ena_rol_oid, ena_ent_oid,
  ena_view, ena_viewasnuser, ena_viewasnwg,
  ena_modify, ena_modifyasnuser, ena_modifyasnwg,
  ena_status_from_oid, ena_status_to_oid,
  ena_new, ena_delete,
  ena_historyview, ena_historynew,
  ena_historymodify, ena_historymodifycreateduser, ena_historymodifycreatedwg,
  ena_historydelete, ena_historydeletecreatedwg, ena_historydeletecreateduser) VALUES
  (4, 281494881711997, 724041768,
      1, NULL, NULL,
      1, NULL, NULL,
      NULL, NULL,
    1, 1,
    NULL, NULL,
    NULL, NULL, NULL,
    NULL, NULL, NULL);

INSERT INTO rep_roles_per_account(rpa_oid, rpa_acc_oid, rpa_rol_oid) VALUES
(1, 1, 281494881712586);
INSERT INTO rep_roles_per_account(rpa_oid, rpa_acc_oid, rpa_rol_oid) VALUES
(2, 1, 281494881711997);
INSERT INTO rep_roles_per_account(rpa_oid, rpa_acc_oid, rpa_rol_oid) VALUES
(3, 1, 281494881711474);

