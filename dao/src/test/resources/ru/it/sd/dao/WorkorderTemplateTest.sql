INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid) VALUES
(1, 633318698189803, 633318698189791, 1);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid) VALUES
(1, 281478244794385, 'com.hp.itsm.common.cl.WFApprovalVoteSet@2f73', 1);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid, tva_atr_aggregation_oid) VALUES
(1, 77050, 3094610126, 1, 665649208);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid, tva_atr_aggregation_oid) VALUES
(1, 665649177, 3095986178, 1, 665649208);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid, tva_atr_aggregation_oid) VALUES
(1, 665649185, 281489493669903, 1, 665649208);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid, tva_atr_aggregation_oid) VALUES
(1, 665649183, 281478323961932, 1, 665649208);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid) VALUES
(1, 878116895, 3095134393, 1);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid) VALUES
(1, 556335107, 'Телефон новому сотруднику:', 1);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid) VALUES
(1, 1032388614, 281478336938129, 1);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid) VALUES
(1, 738983997, 'Необходимо:Создать новый внутренний номер;- Предоставить номер порта- Установить телефон на рабочем месте сотрудника:', 1);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid) VALUES
(1, 281478611337217, 3094610022, 1);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid) VALUES
(1, 281478616973395, 3094610075, 1);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid) VALUES
(1, 854589449, 3095134437, 1);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid) VALUES
(1, 281478292766727, 281488845714827, 1);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid) VALUES
(1, 556335111, 1, 1);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid) VALUES
(1, 281479132938284, 281478302007370, 1);
INSERT INTO rep_template_values (tva_oid, tva_atr_oid, tva_value, tva_tem_oid) VALUES
(1, 4067360799, 'Техподдержка АйТи', 1);


INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
(3095134437,854589446, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
(3095134437, 'Новый(Статус)', 1049);
INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
(3095134393,878116890, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
(3095134393, 'Категория', 1049);
INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
(281478336938129,396748, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
(281478336938129, 'Папка', 1049);
INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
(3095986178,625803273, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
(3095986178, 'Статус назначения', 1049);
INSERT INTO itsm_codes (cod_oid, cod_subtype, cod_ordering, cod_disabled) VALUES
(3094610126,76990, 0, 0);
INSERT INTO itsm_codes_locale (cdl_cod_oid, cdl_name, cdl_lng_oid) VALUES
(3094610126, 'Приоритет назначения', 1049);

INSERT INTO itsm_persons (per_oid, per_name) VALUES
(281489493669903, 'Имя Фамилия Денисовна');
INSERT INTO itsm_workgroups (wog_oid, wog_name) VALUES (
281478323961932, 'Группа поддержки БД');