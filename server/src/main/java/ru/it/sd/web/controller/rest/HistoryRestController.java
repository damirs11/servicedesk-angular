package ru.it.sd.web.controller.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.it.sd.model.PagingRange;
import ru.it.sd.service.History;
import ru.it.sd.service.holder.HistoryServiceHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для работы с историей сущностей, включая чат
 */
@RestController
@RequestMapping(
		name = "/rest/service/history",
		produces = "application/json;charset=UTF-8",
		method = RequestMethod.GET)
public class HistoryRestController {

    private static final Logger LOG = LoggerFactory.getLogger(HistoryRestController.class);

	private final HistoryServiceHolder historyServiceHolder;

	public HistoryRestController(HistoryServiceHolder historyServiceHolder) {
		this.historyServiceHolder = historyServiceHolder;
	}

	/**
	 * Получить информацию об истории изменений сущности
	 *
	 * @param entityType название класса сущности
	 * @param entityId идентификатор экземпляра сущности, для которого получаем историю
	 * @return экземпляр сущности
	 * @throws IllegalArgumentException если указанный в адресной строке класс сущности не был найден, либо произошла ошибка
	 *                                  на этапе извлечения информации из базы данных
	 */
	public List getHistory(@RequestParam String entityType, @RequestParam long entityId,
			@RequestParam Map<String, String> filter, HttpServletResponse response) {
		History historyService = historyServiceHolder.findFor(entityType);
		filter.put("entityId", Long.valueOf(entityId).toString());
		// Проверяем наличие требования к постраничному просмотру.
		// Только в этом случае возвращаем информацию об общем количестве записей
		if (PagingRange.isNeedPaging(filter)) {
			LOG.debug("Need to retrieve amount lines of history");
			int count = historyService.count(filter);
			response.addHeader("X-Amount", Integer.valueOf(count).toString());
		}
		return historyService.list(filter);
	}

	@RequestMapping(method = RequestMethod.POST)
	public void talkToChat(@RequestParam String entityType, @RequestParam long entityId,
	        @RequestBody String message) throws IOException {
		History historyService = historyServiceHolder.findFor(entityType);
		historyService.talkToChat(entityId, message);
	}
}