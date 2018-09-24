package cn.edu.ldxy.jwt.service;

import cn.edu.ldxy.api.UserServiceApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/13
 * @Modified By:
 * @Decription:
 */
@Service("feignUserService")
@FeignClient(value = "feignUserService")
public interface FeignUserService extends UserServiceApi {
}
