package cn.edu.ldxy.tutors.service.impl;

import cn.edu.ldxy.commons.service.impl.MongodbBaseServiceImpl;

import cn.edu.ldxy.tutors.domain.Records;
import cn.edu.ldxy.tutors.domain.Student;
 import cn.edu.ldxy.tutors.repository.RecordsRepository;
import cn.edu.ldxy.tutors.service.RecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecordsServiceImpl extends MongodbBaseServiceImpl<Records, String, RecordsRepository>
							 implements RecordsService {


	@Autowired
	private RecordsRepository recordsRepository;


	@Override
	public Page<Records> findByUsername(String username, Pageable pageable) {
		return this.recordsRepository.findByUsername(username,pageable);
	}

	@Override
	public Page<Records> findByUser(Student student, Pageable pageable) {
		return this.recordsRepository.findByStudent(student,pageable);
	}
}
