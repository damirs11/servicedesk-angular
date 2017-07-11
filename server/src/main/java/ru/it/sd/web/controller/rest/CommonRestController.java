package ru.it.sd.web.controller.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.it.sd.exception.NotFoundException;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.model.HasId;
import ru.it.sd.service.holder.CrudServiceHolder;
import ru.it.sd.service.holder.ReadServiceHolder;
import ru.it.sd.util.EntityUtils;
import ru.it.sd.util.ResourceMessages;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Контроллер CRUDL операций общий для всех сущностей. Операции редактирования
 * работают только с модельными классами из пакета "ru.it.sd.model"
 *
 * @author Vitalii Samolovskikh
 */
@RestController
@RequestMapping("/rest/entity")
@EnableWebMvc
public class CommonRestController {

    private static final Logger LOG = LoggerFactory.getLogger(CommonRestController.class);

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    @Autowired
    private ReadServiceHolder readServiceHolder;
    @Autowired
    private CrudServiceHolder crudServiceHolder;

    @RequestMapping("/ping")
    public String ping() {
        return "OK";
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

    /**
     * Получить список сущностей
     *
     * @param entity название класса сущности
     * @return список сущностей выбранного класса
     * @throws IllegalArgumentException если указанный в адресной строке класс не был найден
     */
    @RequestMapping(value = "/{entity}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public List list(@PathVariable String entity, @RequestParam Map<String, String> filter) {
        return readServiceHolder.findFor(entity).list(filter);
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
    public Map create(@PathVariable String entity, @RequestBody String json) throws IOException {
        HasId obj = (HasId) objectMapper.readValue(json, EntityUtils.getEntityClass(entity));
        Long id = crudServiceHolder.findFor(entity).create(obj).getId();
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return map;
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
    public void update(@PathVariable String entity, @PathVariable long id, @RequestBody String json) throws IOException {
        HasId obj = (HasId) objectMapper.readValue(json, EntityUtils.getEntityClass(entity));
        obj.setId(id); // в приоритете id из адресной строки
        crudServiceHolder.findFor(entity).update(obj);
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
    public void patch(@PathVariable String entity, @PathVariable long id, @RequestBody String json) throws IOException {
        HasId obj = (HasId) objectMapper.readValue(json, EntityUtils.getEntityClass(entity));
        obj.setId(id); // в приоритете id из адресной строки
        Map<String, Object> fields = new ObjectMapper().readValue(json, HashMap.class);
        crudServiceHolder.findFor(entity).patch(obj, fields.keySet());
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