package cn.edu.ldxy.tutors;

import cn.edu.ldxy.tutors.domain.Setting;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SettingApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void saveTest() {

		Setting setting = new Setting();
		setting.setEnable(true);


		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonPost = JSONObject.toJSONString(setting);
		HttpEntity<String> entity = new HttpEntity<>(jsonPost, headers);
		// restTemplate.setUriTemplateHandler(U);
		String body = this.restTemplate.postForObject("/security/tutors/setting/add/", entity, String.class);
		System.err.println(body);

	}

}
