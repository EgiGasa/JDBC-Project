package com.lhind.internship.jdbc.model.enums;

public enum EmployeeQuery {

    SELECT_ALL("SELECT * FROM employees;"),
    SELECT_BY_ID("SELECT * FROM employees WHERE employeeNumber = ?;"),;

    private final String query;

    EmployeeQuery(final String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
