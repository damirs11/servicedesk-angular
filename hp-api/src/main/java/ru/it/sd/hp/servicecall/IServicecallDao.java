package ru.it.sd.hp.servicecall;


import com.hp.itsm.api.interfaces.IClassificationSer;
import com.hp.itsm.api.interfaces.IConfigurationItem;
import com.hp.itsm.api.interfaces.IFolder;
import com.hp.itsm.api.interfaces.IFrequentlyAskedQuestionsGroup;
import com.hp.itsm.api.interfaces.IImpact;
import com.hp.itsm.api.interfaces.IMedium;
import com.hp.itsm.api.interfaces.IOrganization;
import com.hp.itsm.api.interfaces.IPerson;
import com.hp.itsm.api.interfaces.IScClosureCode;
import com.hp.itsm.api.interfaces.IService;
import com.hp.itsm.api.interfaces.IServiceCallCategory;
import com.hp.itsm.api.interfaces.IServicecall;
import com.hp.itsm.api.interfaces.IServicecallCode1;
import com.hp.itsm.api.interfaces.IServicecallCode2;
import com.hp.itsm.api.interfaces.IServicecallCode4;
import com.hp.itsm.api.interfaces.IServicecallCode5;
import com.hp.itsm.api.interfaces.IServicecallCode7;
import com.hp.itsm.api.interfaces.IStatusServicecall;
import com.hp.itsm.api.interfaces.IWorkgroup;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import ru.it.sd.hp.HpApi;
import ru.it.sd.hp.HpCrudDao;
import ru.it.sd.hp.IConfigurationItemDao;
import ru.it.sd.hp.IFolderDao;
import ru.it.sd.hp.IImpactDao;
import ru.it.sd.hp.IOrganizationDao;
import ru.it.sd.hp.IPersonDao;
import ru.it.sd.hp.IServiceDao;
import ru.it.sd.hp.IServiceLevelAgreementDao;
import ru.it.sd.hp.IWorkgroupDao;
import ru.it.sd.hp.utils.DateUtils;
import ru.it.sd.model.ServiceCall;

import java.util.Set;

@Repository
public class IServicecallDao implements HpCrudDao<ServiceCall, IServicecall> {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(IServicecallDao.class);

    private final ApplicationContext context;

    private final HpApi api;
    private final IPersonDao iPersonDao;
    private final IWorkgroupDao iWorkgroupDao;
    private final IImpactDao iImpactDao;
    private final IConfigurationItemDao iConfigurationItemDao;
    private final IFolderDao iFolderDao;
    private final IStatusServicecallDao iStatusServicecallDao;
    private final IServicecallCategoryDao iServicecallCategoryDao;
    private final IOrganizationDao iOrganizationDao;
    private final IServiceLevelAgreementDao iServiceLevelAgreementDao;
    private final IServiceDao iServiceDao;
    private final IClassificationServicecallDao iClassificationServicecallDao;
    private final IScClosureCodeDao iScClosureCodeDao;
    private final IServicecallCode1Dao iServicecallCode1Dao;

    @Autowired
    public IServicecallDao(HpApi api,
                           IPersonDao iPersonDao,
                           IWorkgroupDao iWorkgroupDao,
                           IImpactDao iImpactDao,
                           IConfigurationItemDao iConfigurationItemDao,
                           IFolderDao iFolderDao,
                           IStatusServicecallDao iStatusServicecallDao,
                           IServicecallCategoryDao iServicecallCategoryDao, IOrganizationDao iOrganizationDao, IServiceLevelAgreementDao iServiceLevelAgreementDao, IServiceDao iServiceDao, IClassificationServicecallDao iClassificationServicecallDao, IScClosureCodeDao iScClosureCodeDao, IServicecallCode1Dao iServicecallCode1Dao, ApplicationContext context) {
        this.api = api;
        this.iPersonDao = iPersonDao;
        this.iWorkgroupDao = iWorkgroupDao;
        this.iImpactDao = iImpactDao;
        this.iConfigurationItemDao = iConfigurationItemDao;
        this.iFolderDao = iFolderDao;
        this.iStatusServicecallDao = iStatusServicecallDao;
        this.iServicecallCategoryDao = iServicecallCategoryDao;
        this.iOrganizationDao = iOrganizationDao;
        this.iServiceLevelAgreementDao = iServiceLevelAgreementDao;
        this.iServiceDao = iServiceDao;
        this.iClassificationServicecallDao = iClassificationServicecallDao;
        this.iScClosureCodeDao = iScClosureCodeDao;
        this.iServicecallCode1Dao = iServicecallCode1Dao;
        this.context = context;
    }

    @Override
    public long create(ServiceCall entity) {
        IServicecall servicecall = api.getSdClient().sd_session().getServicecallHome().openNewServicecall(entity.getTemplate() != null ? entity.getTemplate().getId() : null);
        IWorkgroup workgroup = null;
        IPerson executor = null;
        //Назначено
        if (entity.getAssignment() != null) {
            workgroup = iWorkgroupDao.read(entity.getAssignment().getWorkgroup().getId());
            executor = entity.getAssignment().getExecutor() != null ? iPersonDao.read(entity.getAssignment().getExecutor().getId()) : null;
            servicecall.getAssignment().setAssWorkgroup(workgroup);
            servicecall.getAssignment().setAssigneePerson(executor);
            servicecall.getAssignment().transfer();
        }
        //Папка
        if (entity.getFolder() != null) {
            IFolder iFolder = iFolderDao.read(entity.getFolder().getId());
            servicecall.setFolder(iFolder);
        }
        //Статус
        if (entity.getStatus() != null) {
            IStatusServicecall status = iStatusServicecallDao.read(entity.getStatus().getId());
            servicecall.setStatus(status);
        }
        //Источник
        if (entity.getSource() != null) {
            servicecall.setServicecallMedium(context.getBean(IServicecallMediumDao.class).read(entity.getSource().getId()));
        }
        //Время e-mail
        if (entity.getEmailDate() != null) {
            servicecall.setServicecallDate5(DateUtils.toSDDate(entity.getEmailDate()));
        }
        //Категория
        if (entity.getCategory() != null) {
            IServiceCallCategory category = iServicecallCategoryDao.read(entity.getCategory().getId());
            servicecall.setCategory(category);
        }
        //Классификация
        if (entity.getClassification() != null) {
            IClassificationSer iClassificationSer = iClassificationServicecallDao.read(entity.getClassification().getId());
            servicecall.setClassification(iClassificationSer);
        }
        //Код завершения
        if (entity.getClosureCode() != null) {
            IScClosureCode iScClosureCode = iScClosureCodeDao.read(entity.getClosureCode().getId());
            servicecall.setClosureCode(iScClosureCode);
        }
        //Решение
        servicecall.setSolution(entity.getSolution());
        //Инициатор
        if (entity.getInitiator() != null) {
            IPerson requestor = iPersonDao.read(entity.getInitiator().getId());
            servicecall.setRequestor(requestor);
        }
        //Заявитель
        if (entity.getCaller() != null) {
            IPerson caller = iPersonDao.read(entity.getCaller().getId());
            servicecall.setCaller(caller);
        }
        //Организация заявителя
        if (entity.getOrganization() != null) {
            IOrganization iOrganization = iOrganizationDao.read(entity.getOrganization().getId());
            servicecall.setCallerOrganization(iOrganization);
        }
        //Сервис\услуга
        if (entity.getService() != null) {
            IService iService = iServiceDao.read(entity.getService().getId());
            servicecall.setService(iService);
        }
        //Ext ID
        servicecall.setSerShorttext10(entity.getExtId());
        //Объект обслуживания
        if (entity.getConfigurationItem() != null) {
            IConfigurationItem iConfigurationItem = iConfigurationItemDao.read(entity.getConfigurationItem().getId());
            servicecall.setConfigurationItem(iConfigurationItem);
        }
        //Тема
        servicecall.setInformation(entity.getSubject());
        //Информация
        servicecall.setDescription(entity.getDescription());
        //Приоритет
        if (entity.getPriority() != null) {
            IImpact iImpact = iImpactDao.read(entity.getPriority().getId());
            servicecall.setImpact(iImpact);
        }
        //Крайний срок
        if (entity.getDeadline() != null) {
            servicecall.setDeadline(DateUtils.toSDDate(entity.getDeadline()));
        }
        //Оценка
        if (entity.getMark() != null) {
            servicecall.setServicecallCode1(iServicecallCode1Dao.read(entity.getMark().getId()));
        }
        //Функционал
        if (entity.getFunctional() != null) {
            servicecall.setServicecallCode2(context.getBean(IServicecallCode2Dao.class).read(entity.getFunctional().getId()));
        }
        //Часто задаваемые вопросы
        if (entity.getFrequentlyAskedQuestion() != null) {
            servicecall.setFrequentlyAskedQuestion(entity.getFrequentlyAskedQuestion());
        }
        if (entity.getFaq() != null) {
            servicecall.setFrequentlyAskedQuestionsGroup(context.getBean(ServiceCallFaqDao.class).read(entity.getFaq().getId()));
        }
        //Меры по предотвращению
        if (StringUtils.isNotBlank(entity.getPreventionMeasures())) {
            servicecall.setSer4k2(entity.getPreventionMeasures());
        }
        //Уведомление
        if (entity.getNotification() != null) {
            servicecall.setServicecallCode4(context.getBean(IServicecallCode4Dao.class).read(entity.getNotification().getId()));
        }
        //Зона ответственности
        if (entity.getResponsibilityArea() != null) {
            servicecall.setServicecallCode5(context.getBean(IServicecallCode5Dao.class).read(entity.getResponsibilityArea().getId()));
        }
        servicecall.save();
        return servicecall.getOID();
    }

    @Override
    public IServicecall read(long id) {
        return api.getSdClient().sd_session().getServicecallHome().openServicecall(Long.valueOf(id));
    }

    @Override
    public void update(ServiceCall entity, Set<String> fields) {
        IServicecall servicecall = read(entity.getId());
        //Назначено
        if (fields.contains("assignment")) {
            if (entity.getAssignment().getWorkgroup() != null) {
                IWorkgroup workgroup = iWorkgroupDao.read(entity.getAssignment().getWorkgroup().getId());
                servicecall.getAssignment().setAssWorkgroup(workgroup);
            }
            if (entity.getAssignment().getExecutor() != null) {
                IPerson executor = iPersonDao.read(entity.getAssignment().getExecutor().getId());
                servicecall.getAssignment().setAssigneePerson(executor);
            }
            servicecall.getAssignment().transfer();
        }
        //Папка
        if (fields.contains("folder")) {
            IFolder iFolder = iFolderDao.read(entity.getFolder().getId());
            servicecall.setFolder(iFolder);
        }
        //Статус
        if (fields.contains("status")) {
            IStatusServicecall status = iStatusServicecallDao.read(entity.getStatus().getId());
            servicecall.setStatus(status);
        }
        //Источник
        if (fields.contains("source")) {
            IMedium iMedium = entity.getSource() != null ? context.getBean(IServicecallMediumDao.class).read(entity.getSource().getId()) : null;
            servicecall.setServicecallMedium(iMedium);
        }
        //Время e-mail
        if (fields.contains("emailDate")) {
            servicecall.setServicecallDate5(DateUtils.toSDDate(entity.getEmailDate()));
        }
        //Категория
        if (fields.contains("category")) {
            IServiceCallCategory category = iServicecallCategoryDao.read(entity.getCategory().getId());
            servicecall.setCategory(category);
        }
        //Классификация
        if (fields.contains("classification")) {
            IClassificationSer iClassificationSer = iClassificationServicecallDao.read(entity.getClassification().getId());
            servicecall.setClassification(iClassificationSer);
        }
        //Код завершения
        if (fields.contains("closureCode")) {
            IScClosureCode iScClosureCode = iScClosureCodeDao.read(entity.getClosureCode().getId());
            servicecall.setClosureCode(iScClosureCode);
        }
        //Решение
        if (fields.contains("solution")) {
            servicecall.setSolution(entity.getSolution());
        }
        //Инициатор
        if (fields.contains("initiator")) {
            IPerson requestor = iPersonDao.read(entity.getInitiator().getId());
            servicecall.setRequestor(requestor);
        }
        //Заявитель
        if (fields.contains("caller")) {
            IPerson caller = iPersonDao.read(entity.getCaller().getId());
            servicecall.setCaller(caller);
        }
        //Организация заявителя
        if (fields.contains("organization")) {
            IOrganization iOrganization = iOrganizationDao.read(entity.getOrganization().getId());
            servicecall.setCallerOrganization(iOrganization);
        }
        //Сервис\услуга
        if (fields.contains("service")) {
            IService service = iServiceDao.read(entity.getService().getId());
            servicecall.setService(service);
        }
        //Ext ID
        if (fields.contains("extId")) {
            servicecall.setSerShorttext10(entity.getExtId());
        }
        //Объект обслуживания
        if (fields.contains("configurationItem")) {
            IConfigurationItem configurationItem = iConfigurationItemDao.read(entity.getConfigurationItem().getId());
            servicecall.setConfigurationItem(configurationItem);
        }
        //Тема
        if (fields.contains("subject")) {
            servicecall.setDescription(entity.getSubject());
        }
        //Информация
        if (fields.contains("description")) {
            servicecall.setInformation(entity.getDescription());
        }
        //Приоритет
        if (fields.contains("priority")) {
            IImpact impact = iImpactDao.read(entity.getPriority().getId());
            servicecall.setImpact(impact);
        }
        //Крайний срок
        if (fields.contains("deadline")) {
            servicecall.setDeadline(DateUtils.toSDDate(entity.getDeadline()));
        }
        //Выполнено(дата)
        if (fields.contains("resolvedDate")) {
            servicecall.setActualFinish(DateUtils.toSDDate(entity.getResolvedDate()));
        }
        //Закрыто(дата)
        if (fields.contains("closureDate")) {
            IScClosureCode closureCode = iScClosureCodeDao.read(entity.getClosureCode().getId());
            servicecall.setClosureCode(closureCode);
        }
        //Заявка просрочена
        if (fields.contains("expired")) {
            servicecall.setSerBoolean1(entity.getExpired());
        }
        //Кем просрочена
        if (fields.contains("expiredBy")) {
            servicecall.setSerShorttext4(entity.getExpiredBy());
        }
        //Новый крайний срок
        if (fields.contains("newDeadline")) {
            servicecall.setServicecallDate1(DateUtils.toSDDate(entity.getNewDeadline()));
        }
        //Причина переноса крайнего срока
        if (fields.contains("newDeadlineReason")) {
            servicecall.setServicecallText11(entity.getNewDeadlineReason());
        }
        //Руководитель исполнителя
        if (fields.contains("executorHead")) {
            IPerson person = iPersonDao.read(entity.getExecutorHead().getId());
            servicecall.setScPerson1(person);
        }
        //Ошибка при обработке заявки
        if (fields.contains("errorHandling")) {
            servicecall.setSerBoolean10(entity.getErrorHandling());
        }
        //Функциональная эскалация
        if (fields.contains("functionalEscalation")) {
            servicecall.setSerBoolean12(entity.getFunctionalEscalation());
        }
        //Нарушение регистрации
        if (fields.contains("registrationError")) {
            servicecall.setSerBoolean5(entity.getRegistrationError());
        }
        //Оценка
        if (fields.contains("mark")) {
            IServicecallCode1 servicecallCode1 = iServicecallCode1Dao.read(entity.getMark().getId());
            servicecall.setServicecallCode1(servicecallCode1);
        }
        //Комментарий инициатору
        if (fields.contains("commentToInitiator")) {
            servicecall.setWorkaround(entity.getCommentToInitiator());
        }
        //Комментарий исполнителю
        if (fields.contains("commentToExecutor")) {
            servicecall.setSer4k1(entity.getCommentToExecutor());
        }
        //Трудозатраты
        if (fields.contains("laborCosts")) {
            servicecall.setSerDuration1(entity.getLaborCosts() / 1000.0 / 60.0 / 60.0 / 24.0);
        }
        //Функционал
        if (fields.contains("functional")) {
            IServicecallCode2 iServicecallCode2 = entity.getFunctional() != null ? context.getBean(IServicecallCode2Dao.class).read(entity.getFunctional().getId()) : null;
            servicecall.setServicecallCode2(iServicecallCode2);
        }
        //Дата возобновления
        if (fields.contains("renewalDate")) {
            servicecall.setServicecallDate8(DateUtils.toSDDate(entity.getRenewalDate()));
        }
        //Комментарий по приостановке
        if (fields.contains("renewalComment")) {
            servicecall.setServicecallText5(entity.getRenewalComment());
        }
        //Причина приостановки
        if (fields.contains("renewalReason")) {
            IServicecallCode7 iServicecallCode7 = entity.getRenewalReason() != null ? context.getBean(IServicecallCode7Dao.class).read(entity.getRenewalReason().getId()) : null;
            servicecall.setServicecallCode7(iServicecallCode7);
        }
        //Часто задаваемые вопросы
        if (fields.contains("frequentlyAskedQuestion")) {
            servicecall.setFrequentlyAskedQuestion(entity.getFrequentlyAskedQuestion());
        }
        if (fields.contains("faq")) {
            IFrequentlyAskedQuestionsGroup iFrequentlyAskedQuestionsGroup = entity.getFaq() != null ? context.getBean(ServiceCallFaqDao.class).read(entity.getId()) : null;
            servicecall.setFrequentlyAskedQuestionsGroup(iFrequentlyAskedQuestionsGroup);
        }
        //Меры по предотвращению
        if (fields.contains("preventionMeasures")) {
            servicecall.setSer4k2(entity.getPreventionMeasures());
        }
        //Уведомление
        if (fields.contains("notification")) {
            IServicecallCode4 iServicecallCode4 = entity.getNotification() != null ? context.getBean(IServicecallCode4Dao.class).read(entity.getNotification().getId()) : null;
            servicecall.setServicecallCode4(iServicecallCode4);
        }
        //Зона ответственности
        if (fields.contains("responsibilityArea")) {
            IServicecallCode5 iServicecallCode5 =
                    entity.getResponsibilityArea() != null ? context.getBean(IServicecallCode5Dao.class).read(entity.getResponsibilityArea().getId()) : null;
            servicecall.setServicecallCode5(iServicecallCode5);
        }
        servicecall.save();
        servicecall.cancel();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}
