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

    @Scheduled(fixedDelayString = "${sd.job.access.cache.update.delay:30000}", initialDelayString = "${sd.job.access.cache.update.initdelay:2000}")
    @CacheEvict(cacheNames = "access", beforeInvocation = true)
    public void accessUpdating() {
        LOGGER.info("Updating access cache");
        accessService.getList();
        LOGGER.info("Access cache was updated");
    }
}
