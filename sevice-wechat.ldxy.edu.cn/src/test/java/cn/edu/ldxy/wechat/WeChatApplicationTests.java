package cn.edu.ldxy.wechat;

import cn.edu.ldxy.wechat.domain.Message;
import cn.edu.ldxy.wechat.domain.User;
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
public class WeChatApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void saveTest() {


		Message message = new Message();
		message.setTitle("导师选择通知");
		message.setSender("测试");
		User user = new User("20041001","o2b9fxHNSNZC0KYNfaB9wx-EPs6c");
		List<User> userList = new ArrayList<>();
		userList.add(user);
		message.setUserList(userList);
		message.setContent("测试微信消息内容");



		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonPost = JSONObject.toJSONString(message);
		HttpEntity<String> entity = new HttpEntity<>(jsonPost, headers);
		// restTemplate.setUriTemplateHandler(U);
		String body = this.restTemplate.postForObject("/security/weChat/message/add/", entity, String.class);
		System.err.println(body);

	}

}
