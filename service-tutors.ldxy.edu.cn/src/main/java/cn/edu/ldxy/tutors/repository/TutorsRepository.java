package cn.edu.ldxy.tutors.repository;


import cn.edu.ldxy.commons.repository.MongodbBaseRepository;
import cn.edu.ldxy.tutors.domain.Tutors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TutorsRepository extends MongodbBaseRepository<Tutors, String> {


	Page<Tutors> findByUsername(String username, Pageable pageable);

	Tutors findByYearAndUsername(String year, String username);

	//Page<Tutors> findByStudents(List<Student> students, String year, Pageable pageable);




}
