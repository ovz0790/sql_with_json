import com.db.query.config.DataBaseConfiguration;
import com.db.query.db.QueryDao;
import com.db.query.db.QueryDaoImpl;
import com.db.query.util.PropertiesReader;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.activation.UnsupportedDataTypeException;
import java.sql.SQLException;

/**
 * Created by ovz on 28.12.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class QueryDaoTest {

    private static final Logger LOG = LoggerFactory.getLogger(QueryDaoTest.class);

    /**
     * Created by ovz on 09.03.16.
     */

    @Autowired
    QueryDao queryDao;

    @Test
    public void testOracleGetSelectAsJsonSimple() throws UnsupportedDataTypeException, SQLException{
        String sql = "select 1 a, 2 b, 'text' c from dual";
        JSONArray result = queryDao.getSelectAsJson(sql);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.length());
        LOG.info(result.toString());
    }

    @Test
    public void testOracleGetSelectAsJsonSimpleWithParams() throws UnsupportedDataTypeException, SQLException{
        String sql = "select 1 a, 2 b, 'text' c from dual where 1=? and '2'=?";
        JSONArray result = queryDao.getSelectAsJson(sql,new Integer(1), "2");
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.length());
        LOG.info(result.toString());
    }

    //if it's postgres
    @Ignore
    @Test
    public void testPostgresGetSelectAsJsonSimple() throws UnsupportedDataTypeException, SQLException{
        String sql = "select 1 a, 2 b, 'text' c";
        JSONArray result = queryDao.getSelectAsJson(sql);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.length());
        LOG.info(result.toString());
    }

    //if it's postgres
    @Ignore
    @Test
    public void testPostgresGetSelectAsJsonSimpleWithParams() throws UnsupportedDataTypeException, SQLException{
        String sql = "select 1 a, 2 b, 'text' c where 1=? and '2'=?";
        JSONArray result = queryDao.getSelectAsJson(sql,new Integer(1), "2");
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.length());
        LOG.info(result.toString());
    }


    @Configuration
    @Import({
            DataBaseConfiguration.class,
    })
    @PropertySource({
            "${config.path:classpath:config/default.properties}",
    })
    public static class TestConfiguration {

        @Bean
        PropertiesReader propertiesReader(){ return new PropertiesReader();}

        @Bean
        QueryDao queryDao(){
            return new QueryDaoImpl();
        }
    }


}
