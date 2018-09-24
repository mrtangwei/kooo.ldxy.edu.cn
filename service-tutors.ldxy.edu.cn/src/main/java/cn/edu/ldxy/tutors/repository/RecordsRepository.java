package cn.edu.ldxy.tutors.repository;


import cn.edu.ldxy.commons.repository.MongodbBaseRepository;
import cn.edu.ldxy.tutors.domain.Records;
import cn.edu.ldxy.tutors.domain.Student;
 import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecordsRepository extends MongodbBaseRepository<Records, String> {


	Page<Records> findByUsername(String username, Pageable pageable);

	Page<Records> findByStudent(Student user, Pageable pageable);

}
