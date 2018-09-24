package cn.edu.ldxy.auth.repository;

import cn.edu.ldxy.auth.domain.OauthClientDetails;
import cn.edu.ldxy.commons.repository.MongodbBaseRepository;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/16
 * @Modified By:
 * @Decription:
 */
public interface OauthClientDetailsRepository extends MongodbBaseRepository<OauthClientDetails, String> {

    OauthClientDetails findByClientId(String clientId);
    boolean deleteByClientId(String id);
}
