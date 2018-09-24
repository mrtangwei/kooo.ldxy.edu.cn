package cn.edu.ldxy.wechat.controller;


import cn.edu.ldxy.wechat.domain.WeChat;
import cn.edu.ldxy.wechat.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


/**
 * Created by Kooo on 2017/11/10.
 */
@RestController(value = "RestWeChatController")
public class WeChatController{

    @Autowired
    private WeChatService weChatService;





    @PostMapping(value = "/security/weChat/add/")
    public boolean add(@RequestBody WeChat weChat, Principal principal, HttpServletRequest request){
        try{
            this.weChatService.save(weChat);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @GetMapping(value = "/security/weChat/all")
    public Page<WeChat> all(Pageable pageable){
        return this.weChatService.findAll(pageable);
    }








}

