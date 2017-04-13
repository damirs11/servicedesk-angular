package ru.datateh.sd.dao;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс необходимый исключительно для совместимости sql-функции между базами данных.
 * При составлении запроса используется встроенная функция row_number() over (order ...), гарантирующая нам порядок
 * строк в отсортированной таблице. Эта фукция не поддерживается в БД hsqldb.
 * Класс работает как синглтон, единожды при инициализаци определяет тип БД и выставляет признак isOracle.
 */
@Repository
public final class DBUtils extends AbstractDao {
    private static final String ORACLE_PRODUCT_NAME = "Oracle";
    public static final int IN_CAUSE_LIMIT = 500;

    /**
     * Признак поддержки специфичных функций Oracle
     */
    private boolean isOracle;

    private DBUtils() {
    }

    @PostConstruct
    private void init() throws SQLException {
        String productName = jdbc.getDataSource().getConnection().getMetaData().getDatabaseProductName();
        isOracle = ORACLE_PRODUCT_NAME.equalsIgnoreCase(productName);
    }

    public boolean isOracle() {
        return isOracle;
    }

     /**
     * Возвращает значение целочисленного поля. Выполняет проверку на Null
     * @param rs
     * @param columnName название столбца для чтения значения
     * @return null или значение типа Long
     * @throws SQLException в случае ошибки доступа к данным
     */
    public static Long getLong(ResultSet rs, String columnName) throws SQLException {
        Long result = rs.getLong(columnName);
        return rs.wasNull() ? null : result;
    }

    /**
     * Возвращает значение целочисленного поля. Выполняет проверку на Null
     * @param rs
     * @param columnName название столбца для чтения значения
     * @return null или значение типа Integer
     * @throws SQLException в случае ошибки доступа к данным
     */
    public static Integer getInt(ResultSet rs, String columnName) throws SQLException {
        Integer result = rs.getInt(columnName);
        return rs.wasNull() ? null : result;
    }

}