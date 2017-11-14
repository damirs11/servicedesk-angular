package ru.it.sd.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.it.sd.dao.mapper.FolderMapper;
import ru.it.sd.model.Folder;

import java.util.List;

@Repository
public class FolderDao extends AbstractRepCodeDao<Folder> {

	private FolderMapper mapper;

	public FolderDao(FolderMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	protected RowMapper<Folder> getMapper() {
		return mapper;
	}
}


