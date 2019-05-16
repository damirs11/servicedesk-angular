package ru.it.sd.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SDConfig {

    @Value("${sd.application.server}")
    private String sdApplicationServer;
    @Value("${sd.dummy.login}")
    private String dummyLogin;
    @Value("${sd.dummy.password}")
    private String dummyPassword;
    @Value("${sd.ftp.server}")
    private String ftpServer;
    @Value("${sd.ftp.port}")
    private String ftpPort;
    @Value("${sd.ftp.login}")
    private String ftpLogin;
    @Value("${sd.ftp.password}")
    private String ftpPassword;
    @Value("${sd.ftp.homepath}")
    private String ftpHomePath;
    @Value("${sd.upload.webserver.dir}")
    private String uploadWebServerDir;
    @Value("${sd.upload.appserver.dir}")
    private String uploadAppServerDir;
    @Value("${sd.db.url}")
    private String dbUrl;
    @Value("${sd.db.user}")
    private String dbUser;
    @Value("${sd.db.password}")
    private String dbPassword;
    @Value("${sd.db.timezone}")
    private String dbTimezone;
    @Value("${sd.db.readonly:false}")
    private boolean dbReadonly;
    @Value("${sd.db.connection.min:5}")
    private int minConnections;
    @Value("${sd.db.connection.max:10}")
    private int maxConnections;
    @Value("${sd.db.acquire.increment:3}")
    private int acquireIncrement;
    @Value("${sd.db.connection.age:10}")
    private int maxConnectionAge;
    @Value("${sd.db.connection.idle.age:5}")
    private int idleMaxAge;
    @Value("${sd.db.connection.idle.testperiod:1}")
    private int idleConnectionTestPeriod;

    public String getSdApplicationServer() {
        return sdApplicationServer;
    }

    public void setSdApplicationServer(String sdApplicationServer) {
        this.sdApplicationServer = sdApplicationServer;
    }

    public String getDummyLogin() {
        return dummyLogin;
    }

    public void setDummyLogin(String dummyLogin) {
        this.dummyLogin = dummyLogin;
    }

    public String getDummyPassword() {
        return dummyPassword;
    }

    public void setDummyPassword(String dummyPassword) {
        this.dummyPassword = dummyPassword;
    }

    public String getFtpServer() {
        return ftpServer;
    }

    public void setFtpServer(String ftpServer) {
        this.ftpServer = ftpServer;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpLogin() {
        return ftpLogin;
    }

    public void setFtpLogin(String ftpLogin) {
        this.ftpLogin = ftpLogin;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpHomePath() {
        return ftpHomePath;
    }

    public void setFtpHomePath(String ftpHomePath) {
        this.ftpHomePath = ftpHomePath;
    }

    public String getUploadWebServerDir() {
        return uploadWebServerDir;
    }

    public void setUploadWebServerDir(String uploadWebServerDir) {
        this.uploadWebServerDir = uploadWebServerDir;
    }

    public String getUploadAppServerDir() {
        return uploadAppServerDir;
    }

    public void setUploadAppServerDir(String uploadAppServerDir) {
        this.uploadAppServerDir = uploadAppServerDir;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbTimezone() {
        return dbTimezone;
    }

    public void setDbTimezone(String dbTimezone) {
        this.dbTimezone = dbTimezone;
    }

    public boolean isDbReadonly() {
        return dbReadonly;
    }

    public void setDbReadonly(boolean dbReadonly) {
        this.dbReadonly = dbReadonly;
    }

    public int getMinConnections() {
        return minConnections;
    }

    public void setMinConnections(int minConnections) {
        this.minConnections = minConnections;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getAcquireIncrement() {
        return acquireIncrement;
    }

    public void setAcquireIncrement(int acquireIncrement) {
        this.acquireIncrement = acquireIncrement;
    }

    public int getMaxConnectionAge() {
        return maxConnectionAge;
    }

    public void setMaxConnectionAge(int maxConnectionAge) {
        this.maxConnectionAge = maxConnectionAge;
    }

    public int getIdleMaxAge() {
        return idleMaxAge;
    }

    public void setIdleMaxAge(int idleMaxAge) {
        this.idleMaxAge = idleMaxAge;
    }

    public int getIdleConnectionTestPeriod() {
        return idleConnectionTestPeriod;
    }

    public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
        this.idleConnectionTestPeriod = idleConnectionTestPeriod;
    }
}
