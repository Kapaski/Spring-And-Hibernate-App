package me.alanfoster.services.employee.service.impl;

import me.alanfoster.services.employee.dao.IEmployeeDAO;
import me.alanfoster.services.employee.models.IEmployee;
import me.alanfoster.services.employee.models.impl.Job;
import me.alanfoster.services.employee.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Implements the IEmployeeService interface in order to provide a concrete implementation
 *
 * @author Alan Foster
 * @version 1.0.0-SNAPSHOT
 * @see IEmployeeService
 */
@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private IEmployeeDAO employeeDAO;

    @Transactional
    @Override
    public Integer create(IEmployee employee) {
        return employeeDAO.create(employee);
    }

    @Transactional
    @Override
    public IEmployee get(Integer key) {
        return employeeDAO.get(key);
    }

    @Transactional
    @Override
    public List<IEmployee> getAll() {
        return employeeDAO.getAll();
    }

    @Transactional
    @Override
    public void update(IEmployee employee) {
        employeeDAO.update(employee);
    }

    @Transactional
    @Override
    public void delete(IEmployee employee) {
        employeeDAO.delete(employee);
    }

    @Transactional
    @Override
    public void delete(Integer key) {
        employeeDAO.delete(key);
    }
}
