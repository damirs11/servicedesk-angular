package ru.it.sd.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.model.FileInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Маппер персон
 *
 * @author quadrix
 * @since 03.05.2017
 */
@Component
public class FileInfoExtractor extends EntityRowMapper<FileInfo> {

	private WorkgroupDao workgroupDao;

	public FileInfoExtractor(WorkgroupDao workgroupDao) {
		this.workgroupDao = workgroupDao;
	}

	@Override
	public List<FileInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<FileInfo> list = new ArrayList<>();

		while(rs.next()){
            FileInfo fileInfo = super.mapRow(rs, 0);

            //Добавить определение EntityType

			list.add(fileInfo);
		}
		return list;
	}

}