package com.aplana.sd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * Абстрактный класс для работы с базой данных. Содержит базовые объекты
 * для выполнения запросов к БД
 *
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a>
 * @since 02.06.2016 18:20
 */

public abstract class AbstractDao {

	/**
	 * Пул коннектов к БД
	 */
	@Autowired
	protected DataSource dataSource;

	/**
	 * Для запросов с именованными параметрами
	 */
	protected NamedParameterJdbcTemplate namedJdbc;
	/**
	 * Для запросов с анонимными параметрами
	 */
	protected JdbcTemplate jdbc;

	protected SimpleJdbcInsert simpleJdbcInsert;

	/**
	 * Инициализация. Создание объектов для выполнения запросов к бд
	 */
	@PostConstruct
	private void init() {
		namedJdbc = new NamedParameterJdbcTemplate(dataSource);
		jdbc = (JdbcTemplate) namedJdbc.getJdbcOperations();
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource);
	}
}