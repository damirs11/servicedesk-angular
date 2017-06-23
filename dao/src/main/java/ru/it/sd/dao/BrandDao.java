package ru.it.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.BrandMapper;
import ru.it.sd.model.Brand;

import java.text.MessageFormat;
import java.util.List;

@Repository
public class BrandDao extends AbstractCodesDao {

	@Autowired
	private BrandMapper mapper;

	public List<Brand> getAll() {
		return namedJdbc.query(MessageFormat.format(SELECT_ALL_CDL_SQL, ""), (RowMapper) mapper);
	}

	/**
	 * Возвращает бренд по идентификатору
	 * @param id идентификатор бренда
	 * @return бренд
	 */
	public Brand read(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		try {
			Brand brand = namedJdbc.queryForObject(
					MessageFormat.format(SELECT_ALL_CDL_SQL, " AND CDL_COD_OID = :id"),
					params, mapper);
			return brand;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}


