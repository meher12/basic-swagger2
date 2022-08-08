package com.guru.swagger2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guru.swagger2.exception.ResourceNotFoundException;
import com.guru.swagger2.model.Employee;
import com.guru.swagger2.repository.EmployeeRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("api/v1")
@Api(value = "Employee Management System", description = "Operations pertaining to employee in Employee Management System...")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @ApiOperation(value = "View a list of available employees", response = List.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource !"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden !!"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found!") })
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @ApiOperation(value = "Get an employee by Id")
    @GetMapping("employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(
            @ApiParam(value = "Employee id from which employee object will retrieve", required = true) @PathVariable("id") Long id)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id: " + id));
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @ApiOperation(value = "Add an employee")
    @PostMapping("/employees")
    public Employee createEmployee(
            @ApiParam(value = "Employee object store in database table", required = true) @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @ApiOperation(value = "Update an employee")
    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @ApiParam(value = "Employee Id to update employee object", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "Update employee object", required = true) @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found for this id: " + id + "employee not updated"));

        employee.setEmail(employeeDetails.getEmail());
        employee.setLastName(employeeDetails.getLastName());
        employee.setFirstName(employeeDetails.getFirstName());

        Employee employeeUpdated = employeeRepository.save(employee);

        return new ResponseEntity<>(employeeUpdated, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete an employee")
    @DeleteMapping("employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployeeById(  @ApiParam(value = "Employee Id from which employee object will delete from database table", required = true) @PathVariable("id") Long id)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found for this id: " + id + "employee not deleted"));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
