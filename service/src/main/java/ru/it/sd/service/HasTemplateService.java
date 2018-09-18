package ru.it.sd.service;

import ru.it.sd.model.HasId;
import ru.it.sd.model.Template;

import java.util.List;
import java.util.Map;

/**
 * Универсальный сервис предоставляющий операцию получения сущности по шаблону
 *
 * @author nsychev
 * @since 18.09.2018
 */
public interface HasTemplateService<T extends HasId> {

    /**
     * Читает сущность из БД по ее идентификатору
     *
     * @param template шаблон
     * @return объект сущности
     */
    T getTemplate(Template template);

}