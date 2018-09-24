package cn.edu.ldxy.api;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/13
 * @Modified By:
 * @Decription:
 */
@Service
@FeignClient(name = "service-users")
/*public interface FeignUserService extends UserServiceApi {
}*/
public interface FeignUserService {

    @GetMapping("/feign-user-service/{username}")
    JSONObject get(@PathVariable("username") String username);

    @GetMapping(value="/feign-user-service/user/query/{username}/{password}")
    String queryUser(@PathVariable("username") String username, @PathVariable("password") String password);
}