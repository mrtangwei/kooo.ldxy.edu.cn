package cn.edu.ldxy.auth.service.impl;

import cn.edu.ldxy.auth.domain.OauthClientDetails;
import cn.edu.ldxy.auth.repository.OauthClientDetailsRepository;
import cn.edu.ldxy.auth.service.OauthClientDetailsService;
import cn.edu.ldxy.commons.service.impl.MongodbBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/16
 * @Modified By:
 * @Decription:
 */
@Service
public class OauthClientDetailsServiceImpl extends MongodbBaseServiceImpl<OauthClientDetails, String, OauthClientDetailsRepository>
        implements OauthClientDetailsService {

    @Autowired
    private OauthClientDetailsRepository oauthClientDetailsRepository;

    public OauthClientDetails findByClientId(String clientId){
        return    this.oauthClientDetailsRepository.findByClientId(clientId);
    }

    public  boolean deleteByClientId(String clientId){
        return oauthClientDetailsRepository.deleteByClientId(clientId);
    }

}
