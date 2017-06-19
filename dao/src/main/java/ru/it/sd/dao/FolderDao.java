package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.BrandMapper;
import ru.it.sd.dao.mapper.FolderMapper;
import ru.it.sd.model.Brand;
import ru.it.sd.model.Folder;

import java.text.MessageFormat;
import java.util.List;

@Repository
public class FolderDao extends AbstractCodesDao {

	@Autowired
	private FolderMapper mapper;

	public List<Brand> getAll() {
		return namedJdbc.query(MessageFormat.format(SELECT_ALL_CDL_SQL, ""), (RowMapper) mapper);
	}

	/**
	 * Возвращает брэнд по идентификатору
	 * @param id идентификатор брэнда
	 * @return брэнд
	 */
	public Folder read(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		try {
			Folder folder = namedJdbc.queryForObject(
					MessageFormat.format(SELECT_ALL_CDL_SQL, " WHERE RCT_RCD_OID = :id"),
					params, mapper);
			return folder;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}


