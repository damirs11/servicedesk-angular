package ru.it.sd;

import org.testng.annotations.Test;
import ru.it.sd.dao.utils.InMemoryResultSet;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.*;

public class RowSetTest {

    @Test
    public void rowSetTest() throws SQLException {
        ResultSet resultSet = new InMemoryResultSet();
        resultSet.updateString("strTest", "strTest");
        resultSet.updateDouble("dblTest", 0.1);
        resultSet.updateFloat("flt", 0.4f);
        resultSet.updateLong("long", 123L);

        while (resultSet.next()) {
            assertEquals(resultSet.getString("strTest"), "strTest");
            assertEquals(resultSet.getLong("long"), 123L);
            assertEquals(resultSet.getFloat("flt"), 0.4f);
            assertEquals(resultSet.getDouble("dblTest"), 0.1);
        }
    }
}
