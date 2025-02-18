package com.lhind.internship.jdbc.mapper;

import com.lhind.internship.jdbc.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class EmployeeMapper implements Mapper<Employee> {

    private static final EmployeeMapper INSTANCE = new EmployeeMapper();

    private EmployeeMapper() {
    }

    public static EmployeeMapper getInstance() {
        return INSTANCE;
    }

    public Employee toEntity(final ResultSet result) throws SQLException {
        final Employee employee = new Employee();
        employee.setEmployeeNumber(result.getInt("employeeNumber"));
        employee.setFirstName(result.getString("firstName"));
        employee.setLastName(result.getString("lastName"));
        employee.setExtension(result.getString("extension"));
        employee.setEmail(result.getString("email"));
        employee.setOfficeCode(result.getString("officeCode"));
        employee.setReportsTo(result.getInt("reportsTo"));
        employee.setJobTitle(result.getString("jobTitle"));
        return employee;
    }
}
