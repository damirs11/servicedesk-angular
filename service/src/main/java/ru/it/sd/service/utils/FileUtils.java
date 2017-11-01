package ru.it.sd.service.utils;

/**
 * Вспомогательные функции для файлов
 *
 * @author nsychev
 * @since 31.10.2017
 */
public class FileUtils {

    /**
     * Перевод oid(long) в uuid(string), uuid это идентификатор в 16 системе
     * Если после перевода oid в uuid получается меньше 32 символов то недостающие заполняем нулями
     * @param oid id объекта
     * @return возвращает строку в формате HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
     */
    public static String oid2uuid(Long oid){
        String uuid = Long.toHexString(oid);
        while(uuid.length()<32) uuid = "0"+uuid;
        return uuid;
    }
    /**
     * Функция получения полного пути к файлу на ftp сервере
     * @param attachmentOid id вложения(AHS_OID)
     * @param containerOid id контейнера(AHS_ATT_OID)
     * @return возвращает полный путь к файлу на ftp сервере в hex()
     */
    public static String getFTPPathFromOid(Long attachmentOid, Long containerOid){
        String path="";
        String filename = "";
        String attachmentUuid = oid2uuid(attachmentOid);
        String containerUuid = oid2uuid(containerOid);

        for(int i=0;i<32;i++) {
            if(i%3==0) path += '/';
            path += containerUuid.charAt(i);
        }
        path = path.substring(1)+'/';

        for(int i = 0;i<32;i++) {
            if(i==8 || i==12||i==16||i==20)
                filename += "-";
            filename += attachmentUuid.charAt(i);
        }
        return path+filename;
    }
}
