package cn.edu.ldxy.auth.domain;

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

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/16
 * @Modified By:
 * @Decription:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OauthClientDetailsTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void saveTest() {
        JSONObject jsonObject = new JSONObject();
        String j = "{ \n" +
                "    \"clientId\" : \"kooo2\", \n" +
                "    \"clientSecret\" : \"kooo2\", \n" +
                "    \"resourceIds\" : \"app\", \n" +
                "    \"scope\" : \"app\", \n" +
                "    \"authorizedGrantTypes\" : \"client_credentials\", \n" +
                "    \"authorities\" : \"ROLE_USER\"\n" +
                "}";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonPost = j.toString();
        HttpEntity<String> entity = new HttpEntity<>(jsonPost, headers);
        // restTemplate.setUriTemplateHandler(U);
        String body = this.restTemplate.postForObject("/security/oauth/user/add/", entity, String.class);
        System.err.println(body);

    }
}
