package com.db.query.config;

import com.db.query.db.QueryDao;
import com.db.query.db.QueryDaoImpl;
import com.db.query.util.PropertiesReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ovz on 28.12.15.
 */
@Configuration
@Import({
    DataBaseConfiguration.class,
})
@PropertySource({
        "${config.path:classpath:config/default.properties}",
})
public class ApplicationConfiguration {

    @Bean
    PropertiesReader propertiesReader(){ return new PropertiesReader();}

    @Bean
    QueryDao queryDao(){
        return new QueryDaoImpl();
    }
}
