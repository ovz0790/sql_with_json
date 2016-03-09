package com.db.query.db;

import org.json.JSONArray;

import javax.activation.UnsupportedDataTypeException;
import java.sql.SQLException;

/**
 * Created by ovz on 28.12.15.
 */
public interface QueryDao {

    JSONArray getSelectAsJson(String sql, Object... param) throws UnsupportedDataTypeException, SQLException;

}
