package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.ChangeMapper;
import ru.it.sd.dao.mapper.WorkorderMapper;
import ru.it.sd.model.Change;
import ru.it.sd.model.Workorder;

import java.text.MessageFormat;
import java.util.List;

/**
 * Дао для работы с данными нарядов
 */
@Repository
public class WorkorderDao extends AbstractDao {

	@Autowired
	private WorkorderMapper mapper;

	/**
	 * Общий запрос получения данных о наряде
	 */

	private static final String SELECT_ALL_SQL =
			"SELECT" +
					"WORKORDER.WOR_OID as id," +
					"WORKORDER.WOR_ID as no," +
					"WORKORDER.REG_CREATED as createdDate," +
					"WORKORDER.WOR_DEADLINE as deadline," +
					"WORKORDER.REG_MODIFIED as modifyDate" +
					"WORKORDER.WOR_ACTUALFINISH as actualFinish," +
					"WORKORDER.WOR_DESCRIPTION as subject," +
					"INFO.WOI_INFORMATION as description," +
					"WORKORDER.WOR_STA_OID as status.id," +
					"WORKORDER.WOR_CAT_OID as category.id," +
					"WORKORDER.WOR_CLO_OID as closureCode.id," +
					"WORCUSTOM.WCF_BOOLEAN2 as expired," +
					"WORKORDER.WOR_REQUESTOR_PER_OID as initiator.id," +
					"REQUESTOR.PER_NAME as initiator.name," +
					"REQUESTOR.PER_EMAIL as initiator.email," +
					"REQUESTOR.PER_PRIMARYTELNR as initiator.telephone," +
					"REQUESTOR_CUSTOM.PEC_PERSHORTTEXT3 as initiator.avatarId," +
					"WORKORDER.ASS_WORKGROUP as workgroup.id," +
					"WORKGROUP.WOG_NAME as workgroup.name," +
					"WORKORDER.ASS_PER_TO_OID as assigneePerson.id," +
					"ASSPERSON.PER_NAME as assigneePerson.name," +
					"ASSPERSON_CUSTOM.PEC_PERSHORTTEXT3 as assigneePerson.avatarId," +
					"WORCUSTOM.WCF_DURATION1 as labor," +
					"WOR4K1.WO1_4K1 as solution," +
					"SERVICECALL.SER_OID as servicecall.id," +
					"SERVICECALL.SER_ID as servicecall.no," +
					"SERVICECALL.SER_DESCRIPTION as servicecall.subject," +
					"CHANGE.CHA_OID as change.id," +
					"CHANGE.CHA_ID as change.no" +
					"ORGANIZATION.ORG_OID as organization.id," +
					"ORGANIZATION.ORG_NAME1 as organization.name," +
					"PARENTORG.ORG_OID as organization.parent.id," +
					"PARENTORG.ORG_NAME1 as organization.parent.name" +
				"FROM" +
					"ITSM_WORKORDERS as WORKORDER" +
					"LEFT OUTER JOIN REP_CODES as STATUS ON STATUS.RCD_OID = WORKORDER.WOR_STA_OID" +
					"LEFT OUTER JOIN REP_CODES as CATEGORY ON CATEGORY.RCD_OID = WORKORDER.WOR_CAT_OID" +
					"LEFT OUTER JOIN ITSM_WOR_INFORMATION AS INFO ON INFO.WOI_WOR_OID = WORKORDER.WOR_OID" +
					"LEFT OUTER JOIN ITSM_WOR_CUSTOM_FIELDS as WORCUSTOM ON WORCUSTOM.WCF_WOR_OID = WORKORDER.WOR_OID" +
					"LEFT OUTER JOIN ITSM_CHANGES as CHANGE ON CHANGE.CHA_OID = WORKORDER.WOR_CHA_OID" +
					"LEFT OUTER JOIN ITSM_PERSONS as REQUESTOR ON REQUESTOR.PER_OID = WORKORDER.WOR_REQUESTOR_PER_OID" +
					"LEFT OUTER JOIN ITSM_PER_CUSTOM_FIELDS as REQUESTOR_CUSTOM ON REQUESTOR_CUSTOM.PEC_PER_OID = WORKORDER.WOR_REQUESTOR_PER_OID" +
					"LEFT OUTER JOIN ITSM_WORKGROUPS as WORKGROUP ON WORKGROUP.WOG_OID = WORKORDER.ASS_WORKGROUP" +
					"LEFT OUTER JOIN ITSM_PERSONS AS ASSPERSON ON ASSPERSON.PER_OID = WORKORDER.ASS_PER_TO_OID" +
					"LEFT OUTER JOIN ITSM_PER_CUSTOM_FIELDS AS ASSPERSON_CUSTOM ON ASSPERSON_CUSTOM.PEC_PER_OID = WORKORDER.ASS_PER_TO_OID" +
					"LEFT OUTER JOIN ITSM_WOR_4K1 AS WOR4K1 ON WOR4K1.WO1_WOR_OID = WORKORDER.WOR_OID" +
					"LEFT OUTER JOIN ITSM_SERVICECALLS as SERVICECALL on SERVICECALL.SER_OID = WORKORDER.WOR_SER_OID" +
					"LEFT OUTER JOIN ITSM_ORGANIZATIONS as ORGANIZATION ON ORGANIZATION.ORG_OID = WORCUSTOM.WCF_ORG1_OID" +
					"LEFT OUTER JOIN ITSM_ORGANIZATIONS as PARENTORG ON PARENTORG.ORG_OID = ORGANIZATION.ORG_PARENT" +
				"{0}";

	/**
	 * Возвращает наряд по его идентификатору
	 * @param id идентификатор наряда
	 * @return наряд
	 */
	public Workorder findOne(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		try {
			Workorder workorder = namedJdbc.queryForObject(
					MessageFormat.format(SELECT_ALL_SQL, " WHERE wor.wor_oid = :id"),
					params, mapper);
			return workorder;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	/**
	 * Возвращает изменение по идентификатору изменения
	 * @param changeId идентификатор изменения
	 * @return лист нарядов
	 */
	public List<Workorder> findByChange(Long changeId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("changeId", changeId);
		try {
			List<Workorder> workorders = namedJdbc.query(
					MessageFormat.format(SELECT_ALL_SQL, " WHERE change.cha_oid = :changeId"),
					params, ((ResultSetExtractor<List<Workorder>>) mapper));
			return workorders;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	/**
	 * Возвращает все наряды
	 * @return лист нарядов
	 */
	public List<Workorder> findAll() {
		try {
			List<Workorder> workorders = namedJdbc.query(SELECT_ALL_SQL,
					((ResultSetExtractor<List<Workorder>>) mapper));
			return workorders;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}