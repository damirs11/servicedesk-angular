package ru.it.sd.dao;

/**
 * Абстрактный класс для поиска кодов.
 * Здесь единый запрос для получения кодов,
 * который сразу оставляет только русскоязычные
 */

public abstract class AbstractCodesDao extends AbstractDao {


	protected static final String SELECT_ALL_CDL_SQL =
			"SELECT " +
				"CDL_COD_OID, " +
				"CDL_NAME, " +
			"FROM " +
				"ITSM_CODES_LOCALE " +
			"WHERE " +
				"CDL_LNG_OID = 1049 " + // Только на русском
				"{0}";

	protected static final String SELECT_ALL_RCT_SQL =
			"SELECT " +
				"RCD_OID, " +
				"RCT_NAME, " +
			"FROM " +
				"REP_CODES_TEXT " +
			"WHERE " +
				"RCT_LNG_OID = 1049 " + // Только на русском
				"{0}";
}


