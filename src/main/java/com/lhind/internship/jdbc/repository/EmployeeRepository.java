package com.lhind.internship.jdbc.repository;

import com.lhind.internship.jdbc.mapper.EmployeeMapper;
import com.lhind.internship.jdbc.model.Employee;
import com.lhind.internship.jdbc.model.enums.EmployeeQuery;
import com.lhind.internship.jdbc.util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepository implements Repository<Employee, Integer> {

    private static final String SELECT_ALL = "SELECT * FROM employees;";
    private static final String SELECT_BY_ID = "SELECT * FROM employees WHERE employeeNumber = ?;";

    private EmployeeMapper employeeMapper = EmployeeMapper.getInstance();

    @Override
    public List<Employee> findAll() {
        final List<Employee> response = new ArrayList<>();
        try (final Connection connection = JdbcConnection.connect();
             final PreparedStatement statement = connection.prepareStatement(EmployeeQuery.SELECT_ALL.getQuery())) {
            final ResultSet result = statement.executeQuery();
            while (result.next()) {
                response.add(employeeMapper.toEntity(result));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return response;
    }

    @Override
    public Optional<Employee> findById(final Integer id) {
        try (final Connection connection = JdbcConnection.connect();
             final PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);

            final ResultSet result = statement.executeQuery();

            if (result.next()) {
                final Employee employee = employeeMapper.toEntity(result);
                return Optional.of(employee);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean exists(final Integer integer) {
        // TODO: Implement a method which checks if an employee with the given id exists in the employees table
        try (final Connection connection = JdbcConnection.connect();
             final PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM employees WHERE employeeNumber = ?")) {
            statement.setInt(1, integer);
            final ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1) > 0;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;

    }

    @Override
    public Employee save(final Employee employee) {

        /*
         * TODO: Implement a method which adds an employee to the employees table
         *  If the employee exists then the method should instead update the employee
         *
         */
        try (final Connection connection = JdbcConnection.connect()) {
            if (exists(employee.getEmployeeNumber())) {
                // Update existing employee
                try (final PreparedStatement statement = connection.prepareStatement(
                        "UPDATE employees SET lastName = ?, firstName = ?, extension = ?, email = ?, officeCode = ?, reportsTo = ?, jobTitle = ? WHERE employeeNumber = ?")) {
                    statement.setString(1, employee.getLastName());
                    statement.setString(2, employee.getFirstName());
                    statement.setString(3, employee.getExtension());
                    statement.setString(4, employee.getEmail());
                    statement.setString(5, employee.getOfficeCode());
                    statement.setInt(6, employee.getReportsTo());
                    statement.setString(7, employee.getJobTitle());
                    statement.setInt(8, employee.getEmployeeNumber());
                    statement.executeUpdate();
                }
            } else {
                // Insert new employee
                try (final PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO employees (employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
                    statement.setInt(1, employee.getEmployeeNumber());
                    statement.setString(2, employee.getLastName());
                    statement.setString(3, employee.getFirstName());
                    statement.setString(4, employee.getExtension());
                    statement.setString(5, employee.getEmail());
                    statement.setString(6, employee.getOfficeCode());
                    statement.setInt(7, employee.getReportsTo());
                    statement.setString(8, employee.getJobTitle());
                    statement.executeUpdate();
                }
            }
            return employee;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(final Integer integer) {
        /*
         * TODO: Implement a method which deletes an employee given the id
         */

        try (final Connection connection = JdbcConnection.connect();
             final PreparedStatement statement = connection.prepareStatement("DELETE FROM employees WHERE employeeNumber = ?")) {
            statement.setInt(1, integer);
            statement.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    }

