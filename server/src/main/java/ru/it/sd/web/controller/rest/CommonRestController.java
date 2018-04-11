package ru.it.sd.web.controller.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.it.sd.exception.NotFoundException;
import ru.it.sd.meta.MetaUtils;
import ru.it.sd.model.HasId;
import ru.it.sd.service.History;
import ru.it.sd.service.holder.CrudServiceHolder;
import ru.it.sd.service.holder.HistoryServiceHolder;
import ru.it.sd.service.holder.ReadServiceHolder;
import ru.it.sd.util.EntityUtils;
import ru.it.sd.util.ResourceMessages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Контроллер CRUD операций общий для всех сущностей. Операции редактирования
 * работают только с модельными классами из пакета "ru.it.sd.model"
 *
 * @author quadrix
 * @since 22.05.2017
 */
@RestController
@RequestMapping("/rest/entity")
public class CommonRestController {

    private static final Logger LOG = LoggerFactory.getLogger(CommonRestController.class);

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private final ReadServiceHolder readServiceHolder;
    private final CrudServiceHolder crudServiceHolder;
	private final HistoryServiceHolder historyServiceHolder;

	@Autowired
	public CommonRestController(ReadServiceHolder readServiceHolder, CrudServiceHolder crudServiceHolder, HistoryServiceHolder historyServiceHolder) {
		this.readServiceHolder = readServiceHolder;
		this.crudServiceHolder = crudServiceHolder;
		this.historyServiceHolder = historyServiceHolder;
	}

    /**
     * Получить информацию о сущности по её идентификатору
     *
     * @param entity название класса сущности
     * @param id     идентификатор экземпляра сущности
     * @return экземпляр сущности
     * @throws IllegalArgumentException если указанный в адресной строке класс сущности не был найден, либо произошла ошибка
     *                                  на этапе извлечения информации из базы данных
     */
    @RequestMapping(value = "/{entity}/{id}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public Object read(@PathVariable String entity, @PathVariable long id) {
        Object result = readServiceHolder.findFor(entity).read(id);
        if (result == null) {
            throw new NotFoundException(ResourceMessages.getMessage("error.not.found"));
        }
        return result;
    }

	@RequestMapping(value = "/{entity}/{id}/history/count", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public int historyCount(@PathVariable String entity, @PathVariable long id, @RequestParam Map<String, String> filter) {
		History historyService = historyServiceHolder.findFor(entity);
		filter.put("entityId", Long.valueOf(id).toString());
		return historyService.count(filter);
	}

    /**
     * Получить список сущностей
     *
     * @param entity название класса сущности
     * @param filter условие отбора записей, включает в себя также информациою о сортировке и пейджинге
     * @return список сущностей выбранного класса
     * @throws IllegalArgumentException если указанный в адресной строке класс не был найден
     */
    @RequestMapping(value = "/{entity}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public List list(@PathVariable String entity, @RequestParam Map<String, String> filter) {
        if(filter.isEmpty() || filter == null) filter.put("paging", "1;10");
        return readServiceHolder.findFor(entity).list(filter);
    }

    /**
     * Получить количество сущностей по фильтру
     *
     * @param entity название класса сущности
     * @param filter условие отбора записей, включает в себя также информациою о сортировке и пейджинге
     * @return список сущностей выбранного класса
     * @throws IllegalArgumentException если указанный в адресной строке класс не был найден
     */
    @RequestMapping(value = "/{entity}/count", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public int count(@PathVariable String entity, @RequestParam Map<String, String> filter) {
        return readServiceHolder.findFor(entity).count(filter);
    }

    /**
     * Получить мета данные полей
     *
     * @param entity название класса сущности
     * @return мета данные полей
     * @throws IllegalArgumentException если указанный в адресной строке класс не был найден
     */
    @RequestMapping(value = "/{entity}/meta", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public Map meta(@PathVariable String entity) {
        Class clazz = EntityUtils.getEntityClass(entity);
        return MetaUtils.getFieldsMetaData(clazz);
    }
    /**
     * Добавляет сущность в БД
     *
     * @param entity название класса сущности без пакета
     * @param json   JSON представление сущности
     * @throws IOException              если не удается десериализовать сущность
     * @throws IllegalArgumentException если указанный в адресной строке класс сущности не был найден, либо произошла ошибка
     *                                  на этапе извлечения информации из базы данных
     */
    @RequestMapping(value = "/{entity}", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @SuppressWarnings("unchecked")
    public Object create(@PathVariable String entity, @RequestBody String json) throws IOException {
        HasId obj = (HasId) objectMapper.readValue(json, EntityUtils.getEntityClass(entity));
        Long id = crudServiceHolder.findFor(entity).create(obj).getId();
        return readServiceHolder.findFor(entity).read(id);
    }

    /**
     * Обновляет сущность в БД
     *
     * @param entity название класса сущности
     * @param id     идентификатор экземпляра сущности
     * @param json   JSON представление сущности
     * @throws IOException              если не удается десериализовать сущность
     * @throws IllegalArgumentException если указанный в адресной строке класс сущности не был найден, либо произошла ошибка
     *                                  на этапе извлечения информации из базы данных
     */
    @RequestMapping(value = "/{entity}/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @SuppressWarnings("unchecked")
    public Object update(@PathVariable String entity, @PathVariable long id, @RequestBody String json) throws IOException {
        HasId obj = (HasId) objectMapper.readValue(json, EntityUtils.getEntityClass(entity));
        obj.setId(id); // в приоритете id из адресной строки
        return crudServiceHolder.findFor(entity).update(obj);
    }

    /**
     * Обновляет ненулевые поля сущности
     *
     * @param entity название класса сущности
     * @param id     идентификатор экземпляра сущности
     * @param json   JSON представление сущности
     * @throws IOException              если не удается десериализовать сущность
     * @throws IllegalArgumentException если указанный в адресной строке класс сущности не был найден, либо произошла ошибка
     *                                  на этапе извлечения информации из базы данных
     */
    @RequestMapping(value = "/{entity}/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    @SuppressWarnings("unchecked")
    @Deprecated
    public Object patch(@PathVariable String entity, @PathVariable long id, @RequestBody String json) throws IOException {
        HasId obj = (HasId) objectMapper.readValue(json, EntityUtils.getEntityClass(entity));
        obj.setId(id); // в приоритете id из адресной строки
        Map<String, Object> fields = new ObjectMapper().readValue(json, HashMap.class);
        return crudServiceHolder.findFor(entity).patch(obj, fields.keySet());
    }

    /**
     * Удаляет сущность по её идентификатору
     *
     * @param entity название класса сущности
     * @param id     идентификатор экземпляра сущности
     * @throws IllegalArgumentException если указанный в адресной строке класс сущности не был найден, либо произошла ошибка
     *                                  на этапе извлечения информации из базы данных
     */
    @RequestMapping(value = "/{entity}/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String entity, @PathVariable long id) {
        crudServiceHolder.findFor(entity).delete(id);
    }
}