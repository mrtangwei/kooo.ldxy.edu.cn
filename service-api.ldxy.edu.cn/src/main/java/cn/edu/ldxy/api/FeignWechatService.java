package cn.edu.ldxy.api;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/13
 * @Modified By:
 * @Decription:
 */
@Service
@FeignClient(name = "service-wechat")
public interface FeignWechatService {

    @PostMapping("/feign-user-service/wechat/message")
    boolean post(@RequestBody JSONObject wechatMessage);

}