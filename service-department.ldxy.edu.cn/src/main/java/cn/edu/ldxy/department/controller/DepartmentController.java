package cn.edu.ldxy.department.controller;

import cn.edu.ldxy.department.domain.Department;
import cn.edu.ldxy.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/19
 * @Modified By:
 * @Decription:
 */
@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping(value = "/department/all")
    public List<Department> get(){
        return this.departmentService.findAll();
    }

    @PostMapping(value = "/security/department/add")
    public Department save(@RequestBody Department department){
        return this.departmentService.save(department);
    }




}
