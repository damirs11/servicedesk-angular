package ru.it.sd.dao;

/**
 * Интерфейс для поиска кодов.
 */
public interface CodeDao {

	String SELECT_ITSM_SQL =
		"SELECT\n" +
		"	cod.cod_oid id,\n" +
		"	cdl.cdl_name name\n" +
		"FROM\n" +
		"	itsm_codes cod\n" +
		"   LEFT JOIN itsm_codes_locale cdl ON (cdl.cdl_cod_oid = cod.cod_oid AND cdl.cdl_lng_oid = 1049)\n";

	String SELECT_REP_SQL =
		"SELECT\n" +
		"	rcd.rcd_oid id,\n" +
		"	rct.rct_name name\n" +
		"FROM\n" +
		"	rep_codes rcd\n" +
		"	LEFT JOIN rep_codes_text rct ON (rct.rct_rcd_oid = rcd.rcd_oid AND rct.rct_lng_oid = 1049)\n";
}