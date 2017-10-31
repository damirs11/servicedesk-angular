package ru.it.sd.dao;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.FileInfoExtractor;
import ru.it.sd.model.FileInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Дао для работы с вложениями (файлами)
 *
 * @author quadrix
 * @since 28.04.2017
 */
@Repository
public class FileInfoDao extends AbstractEntityDao<FileInfo> {

	private FileInfoExtractor extractor;

	private static final String BASE_SQL =
			" SELECT \n" +
                    "ahs.ahs_oid,\n" +
                    "ahs.ahs_ent_oid,\n" +
                    "ahs.ahs_att_oid,\n" +
                    "ahs.ahs_basename,\n" +
                    "ahs.ahs_filename\n" +
             "FROM \n" +
                    "  rep_attachments ahs\n";

	public FileInfoDao(FileInfoExtractor extractor) {
		this.extractor = extractor;
	}

	@Override
	protected StringBuilder getBaseSql() {
		return new StringBuilder(BASE_SQL);
	}

	@Override
	protected List<FileInfo> executeQuery(String sql, SqlParameterSource params) {
		return namedJdbc.query(sql, params, (ResultSetExtractor<List<FileInfo>>)extractor);
	}

	@Override
	protected void buildWhere(Map<String, String> filter, StringBuilder sql, MapSqlParameterSource params) {
		if (Objects.isNull(filter) || filter.isEmpty()) {
			return;
		}
		super.buildWhere(filter, sql, params);

	}
}