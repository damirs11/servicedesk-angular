package ru.it.sd.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.BrandMapper;
import ru.it.sd.model.Brand;

@Repository
public class BrandDao extends AbstractItsmCodeDao<Brand> {

	private BrandMapper mapper;

	public BrandDao(BrandMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	protected RowMapper<Brand> getMapper() {
		return mapper;
	}
}