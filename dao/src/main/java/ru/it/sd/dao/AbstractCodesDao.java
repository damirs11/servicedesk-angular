package ru.it.sd.dao;

/**
 * Абстрактный класс для поиска кодов.
 * Здесь единый запрос для получения кодов,
 * который сразу оставляет только русскоязычные
 */

public abstract class AbstractCodesDao extends AbstractDao {


	protected static final String SELECT_ALL_CDL_SQL =
			"SELECT " +
				"cdl_cod_oid, " +
				"cdl_name, " +
			"FROM " +
				"itsm_codes_locale " +
			"WHERE " +
				"cdl_lng_oid = 1049 " + // Только на русском
				"{0}";

	protected static final String SELECT_ALL_RCT_SQL =
			"SELECT " +
				"rcd_oid, " +
				"rct_name, " +
			"FROM " +
				"rep_codes_text " +
			"WHERE " +
				"rct_lng_oid = 1049 " + // Только на русском
				"{0}";
}


