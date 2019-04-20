package ru.it.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheJobs {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheJobs.class);

    @Autowired
    private AccessService accessService;

    @Scheduled(fixedDelay = 300000, initialDelay = 2000)
    @CacheEvict(value = "access", beforeInvocation = true)
    public void accessUpdating() {
        LOGGER.info("Updating access cache");
        accessService.getList();
        LOGGER.info("Access cache was updated");
    }
}
