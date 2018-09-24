package cn.edu.ldxy.department.repository;


import cn.edu.ldxy.commons.repository.MongodbBaseRepository;
import cn.edu.ldxy.department.domain.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentRepository extends MongodbBaseRepository<Department, String> {


}
