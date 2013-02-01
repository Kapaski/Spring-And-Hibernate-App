package me.alanfoster.employee.service;


import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import me.alanfoster.services.employee.models.IEmployee;
import me.alanfoster.services.employee.models.impl.Employee;
import me.alanfoster.services.employee.service.IEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import static junit.framework.Assert.*;

import java.util.List;

/**
 * Basic Cucumber StepDefs
 *
 * @author Alan Foster
 * @version 1.0.0-SNAPSHOT
 */
public class StepDefs {
    private static final Logger logger = LoggerFactory.getLogger(StepDefs.class);

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private DataSource dataSource;

    /**
     * Tear down the in-memory database after tests have run to make sure no other tests are affected by previous tests
     * <strong>This has been done this way because spring's @DirtiesContext annotation doesn't work with cucumber jvm</strong>
     */
    @After
    public void tearDown() {
       // JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
       //jdbcTemplate.execute("truncate table Employees");
    }

    @Given("^there is an employee service$")
    public void there_is_an_employee_service() throws Throwable {
        // Autowired
        assertNotNull("Employee service should not be null", employeeService);
    }

    @Given("^an employee with the following details$")
    @When("^the create employee service is called with the following data$")
    public void I_add_the_following_Employee(List<Employee> employees) throws Throwable {
        IEmployee employee = employees.get(0);
        Integer employeeId = employeeService.create(employee);
    }

    @Then("^there will be '(\\d+)' employee in the employee service$")
    public void there_will_be_amount_employee_in_the_employee_service(int expectedAmount) throws Throwable {
        assertEquals("The total amount of employees should be correct", expectedAmount, employeeService.getAll().size());
    }

    @Then("^the employee with id '(\\d+)' will have the following details$")
    public void the_employee_with_id_will_have_the_following_details(int employeeId, List<Employee> employees) throws Throwable {
        IEmployee expectedEmployee = employees.get(0);
        IEmployee actualEmployee = employeeService.get(employeeId);

        assertNotNull("The retrieved employee should not be null", actualEmployee);
        assertEquals("The employee id should be as expected", expectedEmployee.getId(), actualEmployee.getId());
        assertEquals("The first name should be as expected", expectedEmployee.getFirstName(), actualEmployee.getFirstName());
        assertEquals("The second name should be as expected", expectedEmployee.getSecondName(), actualEmployee.getSecondName());
        assertEquals("The job title should be as expected", expectedEmployee.getJobTitle(), actualEmployee.getJobTitle());
        assertEquals("The desk id should be as expected", expectedEmployee.getDeskId(), actualEmployee.getDeskId());
    }

    @When("^the update employee service is called with the following data$")
    public void the_update_employee_service_is_called_with_the_following_data(List<Employee> employees) throws Throwable {
        IEmployee employee = employees.get(0);
        employeeService.update(employee);
    }

    @When("^the delete employee service is called with the employee id '(\\d+)'$")
    public void the_delete_employee_service_is_called_with_the_employee_id_(int employeeId) throws Throwable {
        employeeService.delete(employeeId);
    }
}