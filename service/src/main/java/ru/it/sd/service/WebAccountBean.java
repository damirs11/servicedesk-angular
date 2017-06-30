package ru.it.sd.service;

import com.hp.itsm.api.ApiSDSession;
import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by MYXOMOPX on 030 30.06.17.
 */
@Scope("singleton")
@Component
public class WebAccountBean {
    private SdClientBean sdClient;
    @Autowired
    private Environment env;

    public WebAccountBean(){
        String server = env.getProperty("sd_application_server");
        String login = env.getProperty("sd_dummy_login");
        String password = env.getProperty("sd_dummy_password");
        sdClient = new SdClientBean(server, login, password);
    }

    public SdClientBean getSdClient() {
        return sdClient;
    }

    public ApiSDSession getSession() {
        return sdClient.sd_session();
    }
}
