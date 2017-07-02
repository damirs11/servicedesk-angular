package ru.it.sd.service;

import com.hp.itsm.api.ApiSDSession;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by MYXOMOPX on 030 30.06.17.
 */
@Scope("singleton")
@Component
public class WebAccountBean {
    private SdClientBean sdClient;

    @Value("${sd_application_server}")
    private String server;
    @Value("${sd_dummy_login}")
    private String login;
    @Value("${sd_dummy_password}")
    private String password;

    @PostConstruct
    private void createClient(){
        sdClient = new SdClientBean(server, login, password);
    }

    public SdClientBean getSdClient() {
        return sdClient;
    }

    public ApiSDSession getSession() {
        return sdClient.sd_session();
    }
}
