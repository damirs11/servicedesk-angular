package ru.it.sd.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.it.sd.exception.BadRequestException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;

/**
 * Универсальный десериализатор для перечислимых типов с интерфейсом HasId
 *
 * @author mfayzullin@it.ru
 * @since 06.09.2017 17:17
 */
public class EnumJsonDeserializer<EnumClass extends HasId> extends JsonDeserializer<EnumClass> {

	public EnumJsonDeserializer(){
		super();
	}

	@Override
	@SuppressWarnings("unchecked")
	public EnumClass deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);
		Long id = node.get("id").asLong();
		// Извлекаем класс enum
		Class clazz = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		// Ищем объект с указанным id
		Object[] values = clazz.getEnumConstants();
		for (Object value : values) {
			EnumClass e = (EnumClass) value;
			if (id.equals(e.getId())) {
				return e;
			}
		}
		throw new BadRequestException(MessageFormat.format("Элемента перечисления с идентификатором \"{0}\" " +
				"не существует в классе \"{1}\".", id, clazz.getSimpleName()));
	}
}