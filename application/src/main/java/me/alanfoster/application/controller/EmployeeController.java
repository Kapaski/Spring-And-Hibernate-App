package me.alanfoster.application.controller;

import me.alanfoster.application.model.IEmployee;
import me.alanfoster.application.model.impl.Employee;
import me.alanfoster.application.services.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

/**
 * @author Alan Foster
 * @version 1.0.0-SNAPSHOT
 */
@Controller
@SessionAttributes
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping("/")
    public String index(Map<String, Object> map) {
        logger.info("Received Request for /");

        return EmployeeModelConfig.EmployeeModel.Index.getModel();
    }

    /**
     * Handle adding an employee
     *
     * @param employee
     * @param result
     * @return The redirect to follow - employees.html
     */
    // TODO Use Validation annotations
    @RequestMapping(value = "/employees/add", method = RequestMethod.POST)
    public String addEmployeePost(@ModelAttribute("employee") Employee employee, BindingResult result) {
        employeeService.create(employee);
        return "redirect:/employees/search";
    }

    @RequestMapping("/employees/add")
    public String addEmployee(Map<String, Object> map) {
        logger.info("Received Request for /employees");

        map.put("employee", new Employee());
        map.put("employees", employeeService.getAll());
        map.put("jobTitles", employeeService.getJobTitles());

        return EmployeeModelConfig.EmployeeModel.Add.getModel();
    }

    @RequestMapping(value = "/employees/edit/{employeeId}")
    public String editEmployee(Map<String, Object> map, @PathVariable("employeeId") Integer employeeId) {
        logger.info("Received Request for /edit/{}", new Object[]{employeeId});
        IEmployee employee = employeeService.get(employeeId);
        // Log this and let our presentation layer deal with this scenario
        if (employee == null) {
            logger.debug("Employee Object for employee id {} was null", new Object[]{employeeId});
        }

        map.put("employee", employee);
        map.put("jobTitles", employeeService.getJobTitles());
        return EmployeeModelConfig.EmployeeModel.Edit.getModel();
    }

    @RequestMapping(value = "/employees/search")
    public String searchEmployee(Map<String, Object> map) {
        logger.info("Received Request for /search");
        map.put("employees", employeeService.getAll());
        return EmployeeModelConfig.EmployeeModel.Search.getModel();
    }

    @RequestMapping(value = "/employees/edit/{employeeId}", method = RequestMethod.POST)
    public String editEmployeePost(Map<String, Object> map, @PathVariable("employeeId") Integer employeeId,
                                   @ModelAttribute("employee") Employee employee, BindingResult result,
                                   final RedirectAttributes redirectAttributes) {
        logger.info("Received Request for /edit/{}", new Object[]{employeeId});
        employeeService.update(employee);
        // Add a flash attribute to let the redirected page know of our success
        redirectAttributes.addFlashAttribute("formResult", "success");
        return "redirect:/employees/edit/" + employeeId;
    }

    @RequestMapping(value = "/employees/delete/{employeeId}")
    public String deleteEmployeePost(Map<String, Object> map, @PathVariable("employeeId") Integer employeeId) {
        logger.info("Received Request for /delete/{}", new Object[]{employeeId});
        employeeService.delete(employeeId);
        return "redirect:/employees/search";
    }
}