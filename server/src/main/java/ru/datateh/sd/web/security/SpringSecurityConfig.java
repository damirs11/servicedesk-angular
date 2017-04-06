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
 * quadrix created on 27.12.2015.
 */
@Configuration
@ComponentScan({"ru.datateh.sd.web.security", "ru.datateh.sd.service"})
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
		        // для HTTPS
				.requiresChannel()
					.anyRequest()
					.requiresSecure()
					.and()
                .exceptionHandling()
                	.authenticationEntryPoint(authenticationEntryPoint) //отключает переадресацию, если нет авторизации
                	.and()
		        .authorizeRequests()
		            .antMatchers("/rest/service/config/**").permitAll()
		            .antMatchers("/rest/**").authenticated();
				//	.and();
		        //.formLogin();
    }

    /**
     * Игнорирование указанных адресов
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/img/**")
                .antMatchers("/js/**")
		        .antMatchers("/index.html");
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
}