package cn.edu.ldxy.auth.service;

import cn.edu.ldxy.auth.domain.OauthClientDetails;
import cn.edu.ldxy.auth.repository.OauthClientDetailsRepository;
import cn.edu.ldxy.commons.service.MongodbBaseService;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/16
 * @Modified By:
 * @Decription:
 */
public interface OauthClientDetailsService extends MongodbBaseService<OauthClientDetails, String , OauthClientDetailsRepository> {

    OauthClientDetails findByClientId(String clientId);

    boolean deleteByClientId(String clientId);

}
