package com.db.query.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ovz on 28.12.15.
 */
public class QueryModel {

    public String sql;

    @JsonProperty("parametries")
    public Object[] param;
}
