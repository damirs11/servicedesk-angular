package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * Модельный класс для "Изменения"
 *
 * @author quadrix
 * @since 03.05.2017
 */
@ClassMeta(tableName = "itsm_configuration_items", TableAlias = "ITEM")
public class ConfigurationItem implements HasId, HasStatus, Serializable {

	/** Уникальный идентификатор */
	@FieldMeta(columnName = "CIT_OID")
	private Long id;
	/** Номер */
	@FieldMeta(columnName = "CIT_ID")
	private Long no;
	/** Код поиска */
	@FieldMeta(columnName = "CIT_SEARCHCODE")
	private String searchCode;
	/** Название */
	@FieldMeta(columnName = "CIT_NAME1")
	private String name;
	/** Дополнитеьные сведения */
	@FieldMeta(columnName = "CIT_NAME2")
	private String description;
	/** Счет-фактура */
	@FieldMeta(columnName = "CIT_ORDERNR")
	private String orderNr;
	/** серийный номер */
	@FieldMeta(columnName = "CIT_SERIALNUMBER")
	private String serial;
	/** Адрес */
	@FieldMeta(columnName = "CCF_CITEXT1", TableAlias = "CUSTOM")
	private String address;
	/** Замечание */
	@FieldMeta(columnName = "CIT_REMARK")
	private String remark;
	/** IP-адрес */
	@FieldMeta(columnName = "CIT_IPADDRESS")
	private String ip;
	/** цена */
	@FieldMeta(columnName = "CIT_PRICE")
	private Double price;
	/** Наличие вложений */
	@FieldMeta(columnName = "CIT_ATTACHMENT_EXISTS")
	private Boolean attachmentExists;
	/** Идендификатор аватарки */
	@FieldMeta(columnName = "CCF_CISHORTTEXT1", TableAlias = "CUSTOM")
	private String avatarId;


	/** Статус */
	@FieldMeta(columnName = "CIT_STA_OID")
	private EntityStatus status;
	/** Категория */
	@FieldMeta(columnName = "CIT_CAT_OID")
	private EntityCategory category;
	/** Местоположение */
	@FieldMeta(columnName = "CIT_LOC_OID")
	private Location location;
	/** папка доступа */
	@FieldMeta(columnName = "CIT_POO_OID")
	private Folder folder;
	/** бренд */
	@FieldMeta(columnName = "CIT_BRA_OID")
	private Brand brand;

	/** Дата покупки */
	@FieldMeta(columnName = "CIT_PURCHASEDATE")
	private Date purchaseDate;
	/** Дата гарантии */
	@FieldMeta(columnName = "CIT_WARRANTYDATE")
	private Date warrantyDate;
	/** Дата инвентаризации */
	@FieldMeta(columnName = "CCF_CIDATE1", TableAlias = "CUSTOM")
	private Date inventoryDate;

	/** Администратор объекта */
	@FieldMeta(columnName = "CIT_ADMIN_PER_OID")
	private Person admin;
	/** Владелец */
	@FieldMeta(columnName = "CIT_OWNER_PER_OID")
	private Person owner;
	/** Организация-владелец */
	@FieldMeta(columnName = "CIT_OWNER_ORG_OID")
	private Organization ownerOrganization;
	/** Плательщик */
	@FieldMeta(columnName = "CCF_ORG1_OID", TableAlias = "CUSTOM")
	private Organization payer;
	/** Поставщик */
	@FieldMeta(columnName = "CIT_ORG_OID")
	private Organization supplier;


	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrderNr() {
		return orderNr;
	}

	public void setOrderNr(String orderNr) {
		this.orderNr = orderNr;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getAttachmentExists() {
		return attachmentExists;
	}

	public void setAttachmentExists(Boolean attachmentExists) {
		this.attachmentExists = attachmentExists;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	@Override
	public EntityStatus getStatus() {
		return status;
	}

	@Override
	public void setStatus(EntityStatus status) {
		this.status = status;
	}

	public EntityCategory getCategory() {
		return category;
	}

	public void setCategory(EntityCategory category) {
		this.category = category;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(Date warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	public Date getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public Person getAdmin() {
		return admin;
	}

	public void setAdmin(Person admin) {
		this.admin = admin;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public Organization getOwnerOrganization() {
		return ownerOrganization;
	}

	public void setOwnerOrganization(Organization ownerOrganization) {
		this.ownerOrganization = ownerOrganization;
	}

	public Organization getPayer() {
		return payer;
	}

	public void setPayer(Organization payer) {
		this.payer = payer;
	}

	public Organization getSupplier() {
		return supplier;
	}

	public void setSupplier(Organization supplier) {
		this.supplier = supplier;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, AppToStringStyle.getInstance())
				.append("id", this.id)
				.append("no", this.no)
				.append("searchCode", this.searchCode)
				.append("name", this.name)
				.append("description", this.description)
				.append("orderNr", this.orderNr)
				.append("serial", this.serial)
				.append("address", this.address)
				.append("remark", this.remark)
				.append("ip", this.ip)
				.append("price", this.price)
				.append("attachmentExists", this.attachmentExists)
				.append("avatarId", this.avatarId)
				.append("status", this.status)
				.append("category", this.category)
				.append("purchaseDate", this.purchaseDate)
				.append("warrantyDate", this.warrantyDate)
				.append("inventoryDate", this.inventoryDate)
				.append("admin", this.admin)
				.append("owner", this.owner)
				.append("ownerOrganization", this.ownerOrganization)
				.append("payer", this.payer)
				.append("supplier", this.supplier)
				.toString();
	}
}