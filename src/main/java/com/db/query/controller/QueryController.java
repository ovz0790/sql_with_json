package com.db.query.controller;

import com.db.query.config.ApplicationConfiguration;
import com.db.query.config.WebContainerConfiguration;
import com.db.query.db.QueryDao;
import com.db.query.model.QueryModel;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;

import javax.activation.UnsupportedDataTypeException;
import javax.xml.bind.ValidationException;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by ovz on 28.12.15.
 */
@SpringBootApplication
@RestController
@RequestMapping(QueryController.URL)
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Configuration
@Import({
        ApplicationConfiguration.class,
        WebContainerConfiguration.class,
})
public class QueryController {
    public final static String URL = "/wrap_with_json";

    @Autowired
    QueryDao queryDao;

    @RequestMapping(value = "/{serviceName}", method = RequestMethod.POST)
    public String wrapSelectResult(@PathVariable String serviceName,
                                   @RequestBody QueryModel queryModel) throws UnsupportedDataTypeException,
                                                                              SQLException,
                                                                              ValidationException {
        if (queryModel.sql == null || queryModel.sql.isEmpty()){
            throw new ValidationException("Requested query is empty");
        }

        //TODO: Using separate datasources fro different servicies
        return queryDao.getSelectAsJson(queryModel.sql,
                                        queryModel.param).toString();

    }

    public final static class ControllerConfiguration {

        @Autowired
        public WebContainerConfiguration.WebContainerParameters webContainerParameters;

        @Bean
        public EmbeddedServletContainerFactory servletContainer() {
            JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory(
                    webContainerParameters.getPort() == null ? webContainerParameters.getPort() : 9000);
            if (webContainerParameters.getDisplayName() != null)
                factory.setDisplayName(webContainerParameters.getDisplayName());
            if (webContainerParameters.getErrorPage() != null)
                factory.setErrorPages((Set<ErrorPage>) new ErrorPage(HttpStatus.BAD_REQUEST, webContainerParameters.getErrorPage()));
            if (webContainerParameters.getTimeout() != null)
                factory.setSessionTimeout(webContainerParameters.getTimeout(), TimeUnit.valueOf(webContainerParameters.getTimeoutUnit()));
            return factory;

        }
    }

}
