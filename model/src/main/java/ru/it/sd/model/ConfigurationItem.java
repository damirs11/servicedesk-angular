package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * Модельный класс для "Оборудование"
 *
 * @author quadrix
 * @since 03.05.2017
 */
@ClassMeta(tableName = "itsm_configuration_items", tableAlias = "item")
public class ConfigurationItem implements HasId, HasStatus, Serializable {

	/** Уникальный идентификатор */
	@FieldMeta(columnName = "cit_oid", key = true)
	private Long id;
	/** Номер */
	@FieldMeta(columnName = "cit_id")
	private Long no;
	/** Код поиска */
	@FieldMeta(columnName = "cit_searchcode")
	private String searchCode;
	/** Название */
	@FieldMeta(columnName = "cit_name1")
	private String name;
	/** Дополнитеьные сведения */
	@FieldMeta(columnName = "cit_name2")
	private String description;
	/** Счет-фактура */
	@FieldMeta(columnName = "cit_ordernr")
	private String orderNr;
	/** серийный номер */
	@FieldMeta(columnName = "cit_serialnumber")
	private String serial;
	/** Адрес */
	@FieldMeta(columnName = "ccf_citext1", tableAlias = "custom")
	private String address;
	/** Замечание */
	@FieldMeta(columnName = "cit_remark")
	private String remark;
	/** IP-адрес */
	@FieldMeta(columnName = "cit_ipaddress")
	private String ip;
	/** цена */
	@FieldMeta(columnName = "cit_price")
	private Double price;
	/** Наличие вложений */
	@FieldMeta(columnName = "cit_attachment_exists")
	private Boolean attachmentExists;
	/** Идендификатор аватарки */
	@FieldMeta(columnName = "ccf_cishorttext1", tableAlias = "custom")
	private String avatarId;

	/** Статус */
	@FieldMeta(columnName = "cit_sta_oid")
	private EntityStatus status;
	/** Категория */
	@FieldMeta(columnName = "cit_cat_oid")
	private EntityCategory category;
	/** Местоположение */
	@FieldMeta(columnName = "cit_loc_oid")
	private Location location;
	/** папка доступа */
	@FieldMeta(columnName = "cit_poo_oid")
	private Folder folder;
	/** бренд */
	@FieldMeta(columnName = "cit_bra_oid")
	private Brand brand;

	/** Дата покупки */
	@FieldMeta(columnName = "cit_purchasedate")
	private Date purchaseDate;
	/** Дата гарантии */
	@FieldMeta(columnName = "cit_warrantydate")
	private Date warrantyDate;
	/** Дата инвентаризации */
	@FieldMeta(columnName = "ccf_cidate1", tableAlias = "custom")
	private Date inventoryDate;

	/** Администратор объекта */
	@FieldMeta(columnName = "cit_admin_per_oid")
	private Person admin;
	/** Владелец */
	@FieldMeta(columnName = "cit_owner_per_oid")
	private Person owner;
	/** Организация-владелец */
	@FieldMeta(columnName = "cit_owner_org_oid")
	private Organization ownerOrganization;
	/** Плательщик */
	@FieldMeta(columnName = "ccf_org1_oid", tableAlias = "custom")
	private Organization payer;
	/** Поставщик */
	@FieldMeta(columnName = "cit_org_oid")
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
		return ToStringBuilder.reflectionToString(this, AppToStringStyle.getInstance());
	}
}