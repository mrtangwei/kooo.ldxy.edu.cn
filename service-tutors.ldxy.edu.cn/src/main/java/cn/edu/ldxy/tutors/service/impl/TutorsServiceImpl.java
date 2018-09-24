package cn.edu.ldxy.tutors.service.impl;

import cn.edu.ldxy.commons.service.impl.MongodbBaseServiceImpl;
import cn.edu.ldxy.tutors.domain.Tutors;
import cn.edu.ldxy.tutors.domain.adapter.TutorsGroup;
import cn.edu.ldxy.tutors.repository.TutorsRepository;
import cn.edu.ldxy.tutors.service.TutorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class TutorsServiceImpl extends MongodbBaseServiceImpl<Tutors, String, TutorsRepository>
							 implements TutorsService {


	@Autowired
	private TutorsRepository tutorsRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Page<Tutors> findByUsername(String username, Pageable pageable){
		return this.tutorsRepository.findByUsername(username,pageable);
	}

	@Override
	public Tutors findByYearAndUsername(String year, String username){
		return this.tutorsRepository.findByYearAndUsername( year, username);
	}

	@Override
	public GroupByResults<TutorsGroup> statistic(String year, Pageable pageable){
		GroupByResults<TutorsGroup> results = this.mongoTemplate.group(where("year").is(year),"Tutors",
				GroupBy.key("username").initialDocument("{ count: 0 }").reduceFunction("function(doc, prev) { prev.count += 1 }"),
				TutorsGroup.class);

		return results;
	}

	public List<Tutors> getByYearAndStudent(String year, String username){
		List<Tutors> result = this.mongoTemplate.find(query(where("year").is(year)
				.and("students.username").is(username)), Tutors.class);
		return result;
	}

	public boolean acceptStudent(String year, String username, String studentId){

		//查询选课学生信息
		List<Tutors> result = this.mongoTemplate.find(query(where("year").is(year)
				.and("students.username").is(studentId)), Tutors.class);
		Tutors tutors = result.get(0);
		//List<Student> students = tutors.getStudents();
		tutors.getStudents().stream().filter(x -> {
			if (studentId.equalsIgnoreCase(x.getUsername())){
				x.setAccept(true);
			}
			return true;
		}).collect(Collectors.toList());

		this.tutorsRepository.save(tutors);

/*		for (Student student : students){
			if (student.getUsername() == studentId){
				student.setAccept(true);
			}
		}*/
		return true;

	}

}
