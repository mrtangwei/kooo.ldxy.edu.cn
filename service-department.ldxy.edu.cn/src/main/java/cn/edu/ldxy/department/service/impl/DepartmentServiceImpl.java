package cn.edu.ldxy.department.service.impl;

import cn.edu.ldxy.commons.service.impl.MongodbBaseServiceImpl;

import cn.edu.ldxy.department.domain.Department;
import cn.edu.ldxy.department.repository.DepartmentRepository;
import cn.edu.ldxy.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends MongodbBaseServiceImpl<Department, String, DepartmentRepository>
							 implements DepartmentService {


}
