package ru.datateh.sd.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;

/**
 * Конфигурация контекста Spring
 *
 * @author <a href="mailto:Marat.Fayzullin@aplana.com">Файзуллин Марат</a> created on 27.12.2015.
 */
@Configuration
@ComponentScan({"ru.datateh.sd.security", "ru.datateh.sd.service"})
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppAuthenticationManager authenticationManager;
	@Autowired
	private RestAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * Настройка параметров безопасности, веб-сессии
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
				.csrf()
					.disable()
				.requiresChannel()
					.anyRequest()
					.requiresSecure()
					.and()
                .exceptionHandling()
                	.authenticationEntryPoint(authenticationEntryPoint) //отключает переадресацию, если нет авторизации
                	.and()
                .authorizeRequests()
					.antMatchers("/index.html").authenticated()
					.antMatchers("/rest/**").authenticated()
					.and()
				.httpBasic();
    }

    /**
     * Игнорирование указанных адресов
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/img/**")
                .antMatchers("/js/**");
    }

    @Bean
    public RestAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new RestAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new RestAuthenticationLogoutHandler();
    }

    /**
     * Доп. фильтр необходим чтобы убить сессию вновь воссозданную по сертификату после самого логаута.
     * Тогда при повторном открытии можно будет выбрать нового пользователя, а не останется предыдущий (вне зависимости от выбранного сертификата)
     */
    @Bean
    public Filter getLogoutFilter() {
        LogoutFilter filter = new LogoutFilter(logoutSuccessHandler(), new SecurityContextLogoutHandler(), new CookieClearingLogoutHandler("JSESSIONID"));
        filter.setLogoutRequestMatcher(new AntPathRequestMatcher("/rest/service/securityService/logout"));
        return filter;
    }
}