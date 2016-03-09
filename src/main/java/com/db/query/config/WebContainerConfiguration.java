package com.db.query.config;

import com.db.query.util.PropertiesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by ovz on 28.12.15.
 */
@Configuration
@Import({
       ApplicationConfiguration.class,
})
public class WebContainerConfiguration {

    @Autowired
    PropertiesReader propertiesReader;

    @Bean
    WebContainerParameters webContainerParameters(){
        return new WebContainerParameters(propertiesReader.readPropertyInt("server.port"),
                                          propertiesReader.readPropertyInt("server.timeout"),
                                          propertiesReader.readProperty("server.timeoutUnit"),
                                          propertiesReader.readProperty("server.displayName"),
                                          propertiesReader.readProperty("server.errorPage"));
    }

    public static class WebContainerParameters {

        private final Integer port;
        private final Integer timeout;
        private final String timeoutUnit;
        private final String displayName;
        private final String errorPage;

        public WebContainerParameters(Integer port,
                                      Integer timeout,
                                      String timeoutUnit,
                                      String displayName,
                                      String errorPage) {
            this.port = port;
            this.timeout = timeout;
            this.timeoutUnit = timeoutUnit;
            this.displayName = displayName;
            this.errorPage = errorPage;
        }

        public Integer getPort() {
            return port;
        }

        public Integer getTimeout() {
            return timeout;
        }

        public String getTimeoutUnit() {
            return timeoutUnit;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getErrorPage() {
            return errorPage;
        }

    }
}