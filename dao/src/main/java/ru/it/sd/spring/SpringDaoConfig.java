package ru.it.sd.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация спринга в ДАО-слое
 *
 * @author quadrix
 * @since 11.01.2016
 */
@Configuration
@ComponentScan("ru.it.sd.dao")
public class SpringDaoConfig {
}