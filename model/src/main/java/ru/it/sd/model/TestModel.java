package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.io.Serializable;
import java.util.Date;


/** Модель со всеми типами данных для тестов
 * @author NSychev
 * @since 18.07.2017.
 */
@ClassMeta(tableName = "test_table_name")
public class TestModel implements Serializable, HasStatus, HasId{
    @FieldMeta(columnName = "test_Integer")
    private Integer testInteger;

    @FieldMeta(columnName = "test_int")
    private int testint;

    @FieldMeta(columnName = "test_Long")
    private Long testLong;

    @FieldMeta(columnName = "test_long")
    private long testlong;

    @FieldMeta(columnName = "test_Double")
    private Double testDouble;

    @FieldMeta(columnName = "test_double")
    private double testdouble;

    @FieldMeta(columnName = "test_String")
    private String testString;

    @FieldMeta(columnName = "test_Boolean")
    private Boolean testBoolean;

    @FieldMeta(columnName = "test_boolean")
    private boolean testboolean;

    @FieldMeta(columnName = "test_Date")
    private Date testDate;

    @FieldMeta(columnName = "test_EntityStatus")
    private EntityStatus testEntityStatus;

    @FieldMeta(columnName = "test_EntityCategory")
    private EntityCategory testEntityCategory;

    @FieldMeta(columnName = "test_Location")
    private Location testLocation;

    @FieldMeta(columnName = "test_Folder")
    private Folder testFolder;

    @FieldMeta(columnName = "test_Brand")
    private Brand testBrand;

    @FieldMeta(columnName = "test_Person")
    private Person testPerson;

    @FieldMeta(columnName = "test_Organization")
    private Organization testOrganization;

    @Override
    public EntityStatus getStatus() {
        return null;
    }

    @Override
    public void setStatus(EntityStatus id) {

    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {

    }

    public Integer getTestInteger() {
        return testInteger;
    }

    public void setTestInteger(Integer testInteger) {
        this.testInteger = testInteger;
    }

    public int getTestint() {
        return testint;
    }

    public void setTestint(int testint) {
        this.testint = testint;
    }

    public Long getTestLong() {
        return testLong;
    }

    public void setTestLong(Long testLong) {
        this.testLong = testLong;
    }

    public long getTestlong() {
        return testlong;
    }

    public void setTestlong(long testlong) {
        this.testlong = testlong;
    }

    public Double getTestDouble() {
        return testDouble;
    }

    public void setTestDouble(Double testDouble) {
        this.testDouble = testDouble;
    }

    public double getTestdouble() {
        return testdouble;
    }

    public void setTestdouble(double testdouble) {
        this.testdouble = testdouble;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public Boolean getTestBoolean() {
        return testBoolean;
    }

    public void setTestBoolean(Boolean testBoolean) {
        this.testBoolean = testBoolean;
    }

    public boolean isTestboolean() {
        return testboolean;
    }

    public void setTestboolean(boolean testboolean) {
        this.testboolean = testboolean;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public EntityStatus getTestEntityStatus() {
        return testEntityStatus;
    }

    public void setTestEntityStatus(EntityStatus testEntityStatus) {
        this.testEntityStatus = testEntityStatus;
    }

    public EntityCategory getTestEntityCategory() {
        return testEntityCategory;
    }

    public void setTestEntityCategory(EntityCategory testEntityCategory) {
        this.testEntityCategory = testEntityCategory;
    }

    public Location getTestLocation() {
        return testLocation;
    }

    public void setTestLocation(Location testLocation) {
        this.testLocation = testLocation;
    }

    public Folder getTestFolder() {
        return testFolder;
    }

    public void setTestFolder(Folder testFolder) {
        this.testFolder = testFolder;
    }

    public Brand getTestBrand() {
        return testBrand;
    }

    public void setTestBrand(Brand testBrand) {
        this.testBrand = testBrand;
    }

    public Person getTestPerson() {
        return testPerson;
    }

    public void setTestPerson(Person testPerson) {
        this.testPerson = testPerson;
    }

    public Organization getTestOrganization() {
        return testOrganization;
    }

    public void setTestOrganization(Organization testOrganiztion) {
        this.testOrganization = testOrganiztion;
    }
}
