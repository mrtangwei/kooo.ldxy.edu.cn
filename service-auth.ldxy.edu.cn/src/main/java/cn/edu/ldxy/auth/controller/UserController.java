package cn.edu.ldxy.auth.controller;

import cn.edu.ldxy.api.FeignUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <p>必须要有，做验证</p>
 * Created by Mr.Yangxiufeng on 2017/12/29.
 * Time:10:43
 * ProjectName:Mirco-Service-Skeleton
 */
@RestController
public class UserController {

    @Autowired
    private FeignUserService feignUserService;

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping(value="/users/query/{username}/{password}")
    public  String queryUser(@PathVariable("username")String username, @PathVariable("password")String password){

        return this.feignUserService.queryUser(username,password);

    }
/*
    @GetMapping(value = "/login/success")
    public @ResponseBody
    JSONObject login(Principal principal, HttpServletRequest request) {

        if (principal != null){
            return this.feignUserService.get(principal.getName());
        }else{
            return null;
        }
    }*/
}
