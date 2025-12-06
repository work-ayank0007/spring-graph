package com.example.demo.mapper;

import com.example.demo.entity.employeeEntity;
import com.example.demo.repository.employeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class employeeMapper {

    private final employeeRepository repository;

    // Get all employees
    @QueryMapping
    public List<employeeEntity> employees() {
        return repository.findAll();
    }

    // Add employee
    @MutationMapping
    public employeeEntity addEmployee(
            @Argument String name,
            @Argument String email,
            @Argument String department,
            @Argument Double salary) {

        employeeEntity employee = new employeeEntity();
        employee.setName(name);
        employee.setEmail(email);
        employee.setDepartment(department);
        employee.setSalary(salary);

        return repository.save(employee);
    }

    // Update employee
    @MutationMapping
    public employeeEntity updateEmployee(
            @Argument Long id,
            @Argument String name,
            @Argument String email,
            @Argument String department,
            @Argument Double salary) {

        Optional<employeeEntity> optionalEmployee = repository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new RuntimeException("Employee not found with id: " + id);
        }

        employeeEntity employee = optionalEmployee.get();

        if (name != null) employee.setName(name);
        if (email != null) employee.setEmail(email);
        if (department != null) employee.setDepartment(department);
        if (salary != null) employee.setSalary(salary);

        return repository.save(employee);
    }

    // Delete employee
    @MutationMapping
    public Boolean deleteEmployee(@Argument Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
