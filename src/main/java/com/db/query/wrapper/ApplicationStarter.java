package com.db.query.wrapper;

import com.db.query.controller.QueryController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

/**
 * Created by ovz on 28.12.15.
 */
public class ApplicationStarter {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationStarter.class);
    public static void main(String[] args) throws Throwable {
        LOG.info("Starting HTTP Container");
        SpringApplication.run(QueryController.class);
    }


}
