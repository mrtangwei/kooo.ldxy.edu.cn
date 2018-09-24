package cn.edu.ldxy.tutors;

import cn.edu.ldxy.tutors.domain.Student;
import cn.edu.ldxy.tutors.domain.Tutors;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TutorsApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void saveTest() {

		Tutors tutors = new Tutors();
		tutors.setUsername("20041004");
		tutors.setRealName("李存勖");
		tutors.setYear("2017-2018");
		List<Student> studentList = new ArrayList<>();

		Student student = new Student();
		student.setUsername("2018001005");
		student.setRealName("邱少波");
		studentList.add(student);

		Student student2 = new Student();
		student2.setUsername("2018001006");
		student2.setRealName("何大海");


		studentList.add(student2);
		tutors.setStudents(studentList);



		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonPost = JSONObject.toJSONString(tutors);
		HttpEntity<String> entity = new HttpEntity<>(jsonPost, headers);
		String body = this.restTemplate.postForObject("/feign-tutors-service/tutors/document/add/", entity, String.class);
		System.err.println(body);

	}


	@Test
	public void findTest(){

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		//String jsonPost = JSONObject.toJSONString(tutors);
		//HttpEntity<String> entity = new HttpEntity<>(jsonPost, headers);
		///feign-tutors-service/tutors/accept/{year}/{username}/{studentId}/
		String body = this.restTemplate.getForObject("/feign-tutors-service/tutors/student/2017-2018/2018001005",String.class);
		System.out.println("---------------------------------------------------------------");
		System.out.println("---------------------------------------------------------------");
		System.out.println("------  												-------");
		System.out.println(body);
		System.out.println("------  												-------");
		System.out.println("---------------------------------------------------------------");
		System.out.println("---------------------------------------------------------------");

	}
	@Test
	public void accept(){

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		//String jsonPost = JSONObject.toJSONString(tutors);
		//HttpEntity<String> entity = new HttpEntity<>(jsonPost, headers);
		///feign-tutors-service/tutors/accept/2017-2018/20041004/2018001005/
		String body = this.restTemplate.getForObject("/feign-tutors-service/tutors/accept/2017-2018/20041004/2018001005/",String.class);
		System.out.println("---------------------------------------------------------------");
		System.out.println("---------------------------------------------------------------");
		System.out.println("------  												-------");
		System.out.println(body);
		System.out.println("------  												-------");
		System.out.println("---------------------------------------------------------------");
		System.out.println("---------------------------------------------------------------");

	}

}
