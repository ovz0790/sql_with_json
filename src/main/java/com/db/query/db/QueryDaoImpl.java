package com.db.query.db;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;

import javax.activation.UnsupportedDataTypeException;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ovz on 28.12.15.
 */

public class QueryDaoImpl implements QueryDao{
    private static final Logger LOG = LoggerFactory.getLogger( QueryDaoImpl.class);
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private JdbcTemplate jdbcTemplate;

    final private RowMapper<JSONObject> JSONARRAYROWMAPPER = (rs, rowIndex) -> {
        JSONObject jrow = new JSONObject();
        int columnCount = rs.getMetaData().getColumnCount();
        for (int i=1;i<=columnCount; i++){
            String fieldName= rs.getMetaData().getColumnName(i);
            try {
                Object fieldVal = getValueByType(rs, i);
                //TODO: date format using
                jrow.put(fieldName, fieldVal);
            } catch (UnsupportedDataTypeException e){
                LOG.error("ERROR PARSING QUERY RESULT ", e);
            }
        }
        return jrow;
    };

    public JSONArray getSelectAsJson(String sql, Object... param) throws UnsupportedDataTypeException, SQLException {
        JSONArray result = new JSONArray();
        List<JSONObject> rows;
        rows = jdbcTemplate.query(sql, param, JSONARRAYROWMAPPER);
        if (rows != null) {
            for (int j = 0; j < rows.size(); j++) {
                result.put(j, rows.get(j));
            }
            return result;
        } else {
            return null;
        }

    }

    private Object getValueByType(ResultSet res, int columnInd) throws UnsupportedDataTypeException, SQLException {
        int typeCode = res.getMetaData().getColumnType(columnInd) ;

        if (Types.getIntegerTypes().contains(typeCode)) {
            return res.getInt(columnInd);
        } else if (Types.getDateTypes().contains(typeCode) ||
                Types.getStringTypes().contains(typeCode)) {
            return res.getString(columnInd);
        } else if (Types.getDoubleTypes().contains(typeCode)) {
            return res.getDouble(columnInd);
        }
        throw new UnsupportedDataTypeException("No supported data types");
    }

}
