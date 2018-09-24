package cn.edu.ldxy.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mr.Yangxiufeng on 2017/12/27.
 * Time:17:02
 * ProjectName:Mirco-Service-Skeleton
 */
@RestController
public class HelloController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/risk/hello")
    public String hello() {
        return "hello kitty";
    }

/*    @RequestMapping("/login")
    public String login() {
        return "login";
    }*/


    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public void doLogin(String username,String password){
        System.out.println(username);
    }
}
