package cn.edu.ldxy.department.service;


import cn.edu.ldxy.commons.service.MongodbBaseService;

import cn.edu.ldxy.department.domain.Department;
import cn.edu.ldxy.department.repository.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentService extends MongodbBaseService<Department, String, DepartmentRepository> {



}
