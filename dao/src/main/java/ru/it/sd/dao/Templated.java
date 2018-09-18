package ru.it.sd.dao;

import ru.it.sd.model.HasId;
import ru.it.sd.model.Template;

import java.sql.SQLException;


public interface Templated<T extends HasId> {
    T getTemplate(Template template) throws SQLException;
}
