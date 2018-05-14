package ru.it.sd.web.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.it.sd.model.EntityHistory;
import ru.it.sd.model.HasId;
import ru.it.sd.model.HistoryType;
import ru.it.sd.model.PagingRange;
import ru.it.sd.service.History;
import ru.it.sd.service.holder.HistoryServiceHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для работы с историей сущностей, включая чат
 */
@RestController
@RequestMapping(
		value = "/rest/service/history",
		produces = "application/json;charset=UTF-8")
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
	@RequestMapping(method = RequestMethod.GET)
	public List<EntityHistory> getHistory(@RequestParam String entityType, @RequestParam long entityId,
	                                      @RequestParam Map<String, String> filter, HttpServletResponse response) {
		History<HasId, EntityHistory> historyService = historyServiceHolder.findFor(entityType);
		filter.put("entityId", Long.valueOf(entityId).toString());
		// Проверяем наличие требования к постраничному просмотру.
		// Только в этом случае возвращаем информацию об общем количестве записей
		if (PagingRange.isNeedPaging(filter)) {
			LOG.debug("Need to retrieve amount lines of history");
			int count = historyService.count(filter);
			PagingRange range = PagingRange.fromFilter(filter);
			response.addHeader("Content-Range",
					MessageFormat.format("{0}-{1}/{2}",
							range.getFrom(),
							range.getTo(),
							Integer.valueOf(count).toString()));
		}
		return historyService.list(filter);
	}

	@RequestMapping(method = RequestMethod.POST)
	public void talkToChat(@RequestParam String entityType, @RequestParam long entityId,
						   @RequestBody String message, @RequestParam String historyType) throws IOException {
		History historyService = historyServiceHolder.findFor(entityType);
		HistoryType type = HistoryType.getByName(historyType);
		historyService.talkToChat(entityId, message, type);
	}
}