package ru.datateh.sd.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Простой логаут без перенаправления на страницу логина
 *
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a> created on 16.03.2016.
 */
public class RestAuthenticationLogoutHandler extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    }
}