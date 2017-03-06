package ru.datateh.sd.web.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Класс отключает автоматическое перенаправление на страницу для ввода логина\пароля.
 * Вместо этого возращается ответ 401.
 *
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a> created on 16.03.2016.
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}