package ru.it.sd.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Objects;

import static ru.it.sd.model.EntityType.*;

/**
 * Классификации сущностей
 */
@Deprecated
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = EntityClassification2.Deserializer.class)
public enum EntityClassification2 implements Code {


	CHANGE_INCEDENT("Инцидент/проблема", CHANGE, 3095134296L),
	CHANGE_DISSATISFACTION("Неудовлетворенность Потребителя", CHANGE, 3095134301L),
	CHANGE_NEW_CI("Неудовлетворенность Потребителя", CHANGE, 3095134302L),
	CHANGE_UPGRADE("Улучшение компонент", CHANGE, 3095134309L),
	CHANGE_BUISNESS_REQUIREMENTS("Бизнес требования", CHANGE, 3095134310L),
	CHANGE_UPDATE_LEGISLATION("Вызвано изменением Законодательства", CHANGE, 3095134317L),
	CHANGE_UPDATE_PRODUCT("Вызвано изменением продукта/услуги  третьей стороны", CHANGE, 3095134321L),
	CHANGE_REMOVE_CI("Вызвано удалением 'ОБЪЕКТА ОБСЛУЖИВАНИЯ'( CI)", CHANGE, 3120234534L),
	CHANGE_UPDATE_RELEASE("Обновление релиза", CHANGE, 281496411242933L);

	/** Идентификатор классификации */
	private Long id;
	/** Название классификации */
	private String name;
	/** Тип сущности, к которой относится классификация */
	private EntityType entityType;

	EntityClassification2(String name, EntityType entityType, Long id) {
		this.name = name;
		this.entityType = entityType;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}
	@Override
	public Integer getOrder() {
		return null;
	}

	@Override
	public void setOrder(Integer order) {

	}
	/**
	 * Поиск классификации по её идентификатору
	 * @return категория, либо null, если указанного идентификатора нет
	 */
	public static EntityClassification2 getById(Long id) {
		Objects.requireNonNull(id);
		for(EntityClassification2 classification : EntityClassification2.values()) {
			if (id.equals(classification.id)) {
				return classification;
			}
		}
		return null;
	}

	static class Deserializer extends EnumJsonDeserializer<EntityClassification2> {}
}