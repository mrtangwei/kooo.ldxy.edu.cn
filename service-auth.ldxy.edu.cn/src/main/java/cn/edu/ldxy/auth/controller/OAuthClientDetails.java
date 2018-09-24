package cn.edu.ldxy.auth.controller;

import cn.edu.ldxy.auth.domain.OauthClientDetails;
import cn.edu.ldxy.auth.service.OauthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/16
 * @Modified By:
 * @Decription:
 */
@RestController
public class OAuthClientDetails {

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @PostMapping("/security/oauth/user/add/")
    public OauthClientDetails add(@RequestBody OauthClientDetails oauthClientDetails){
        oauthClientDetails.setClientSecret(new BCryptPasswordEncoder().encode(oauthClientDetails.getClientSecret()));
        return         this.oauthClientDetailsService.save(oauthClientDetails);

    }

}
