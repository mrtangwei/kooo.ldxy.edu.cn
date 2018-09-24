package cn.edu.ldxy.wechat.service.impl;



import cn.edu.ldxy.commons.service.impl.MongodbBaseServiceImpl;
import cn.edu.ldxy.wechat.domain.WeChat;
import cn.edu.ldxy.wechat.repository.WeChatRepository;
import cn.edu.ldxy.wechat.service.WeChatService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Kooo
 * 
 */
@Service
public class WeChatServiceImpl extends MongodbBaseServiceImpl<WeChat, String, WeChatRepository>
		implements WeChatService {

	private String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxabdf564ae3a8118e&secret=058b0a3b6fcebb1e7918c2e864786706";


	@Autowired(required = true)
	private RestTemplate restTemplate;


	@Override
	public String getAccessToken(){
		try {

			JSONObject accessToken = JSONObject.parseObject(this.restTemplate.getForEntity(this.url, String.class).getBody());

			return accessToken.getString("access_token");

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
