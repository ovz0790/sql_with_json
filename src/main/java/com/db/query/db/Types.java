package com.db.query.db;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ovz on 28.12.15.
 */
public enum Types {
    BIT (7),
    INTEGER(4),
    BIGINT(-5),
    FLOAT(6),
    DOUBLE(8),
    NUMERIC(2),
    DECIMAL(3),
    CHAR(1),
    VARCHAR(12),
    LONGVARCHAR(-1),
    DATE(91),
    TIME(92),
    TIMESTAMP(93);

    public static Set getIntegerTypes(){
        return new HashSet(Arrays.asList(4, -5));
    }

    public static Set getDoubleTypes(){
        return new HashSet(Arrays.asList(6,8,3,2));
    }

    public static Set getDateTypes(){
        return new HashSet(Arrays.asList(91,92,93));
    }

    public static Set getStringTypes(){
        return new HashSet(Arrays.asList(-1,1,12));
    }

    public int getTypeCode() {
        return typeCode;
    }

    private final int typeCode;

    Types(int typeCode) {
        this.typeCode = typeCode;
    }
}
