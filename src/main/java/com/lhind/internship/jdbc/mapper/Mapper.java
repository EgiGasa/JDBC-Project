package com.lhind.internship.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<ENTITY> {

    ENTITY toEntity(final ResultSet result) throws SQLException;
}
