package ru.it.sd.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.util.AppToStringStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Модельный класс для хранения информации о данных с разбивкой по страницам
 *
 * @author quadrix
 * @since 13.06.2017
 */
@JsonSerialize(using = PagingList.CustomSerializer.class)
public class PagingList<T> extends ArrayList<T> {

	/**
	 * Общее количество строк
	 */
	private int total;

	public PagingList() {
		super();
	}

	public PagingList(List<T> list, int total) {
		this.total = total;
		addAll(list);
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, AppToStringStyle.getInstance())
				.append("list", super.toArray())
				.append("total", this.total)
				.toString();
	}

	public static class CustomSerializer extends JsonSerializer<PagingList> {

		@Override
		public void serialize(PagingList value, JsonGenerator json, SerializerProvider provider) throws IOException {
			json.writeStartObject();
			json.writeFieldName("total");
			json.writeString(String.valueOf(value.getTotal()));
			json.writeFieldName("list");
			json.writeStartArray(value.size());
			for(int i=0; i<value.size(); i++) {
				json.writeObject(value.get(i));
			}
			json.writeEndArray();
			json.writeEndObject();
			json.close();
		}
	}

}