package cn.edu.ldxy.auth.service;

import cn.edu.ldxy.auth.domain.OauthClientDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/16
 * @Modified By:
 * @Decription: 自定义实现OAUTH2持久化接口
 */
public class MongodbClientDetailsService implements ClientDetailsService, ClientRegistrationService {

    private static final Log logger = LogFactory.getLog(MongodbClientDetailsService.class);


    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        BaseClientDetails baseClientDetails;
        try {
            OauthClientDetails oauthClientDetails = this.oauthClientDetailsService.findByClientId(clientId);

            if (oauthClientDetails != null){
                baseClientDetails = new BaseClientDetails(oauthClientDetails.getClientId(),
                        oauthClientDetails.getResourceIds(), oauthClientDetails.getScope(),
                        oauthClientDetails.getAuthorizedGrantTypes(), oauthClientDetails.getAuthorities(),
                        oauthClientDetails.getWebServerRedirectUri());
                baseClientDetails.setClientSecret(oauthClientDetails.getClientSecret());
                if (oauthClientDetails.getAccessTokenValidity() != null) {
                    baseClientDetails.setAccessTokenValiditySeconds(oauthClientDetails.getAccessTokenValidity());
                }
                if (oauthClientDetails.getRefreshTokenValidity() != null) {
                    baseClientDetails.setRefreshTokenValiditySeconds(oauthClientDetails.getRefreshTokenValidity());
                }
                String json = oauthClientDetails.getAdditionalInformation().entrySet().toString();

                if (json != null) {
                    // Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();
                    try {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();
                        additionalInformation.put(json,Map.class);
                        baseClientDetails.setAdditionalInformation(additionalInformation);
                    }
                    catch (Exception e) {
                        logger.warn("Could not decode JSON for additional information: " + baseClientDetails, e);
                    }
                }
                String scopes = oauthClientDetails.getScope();
                if (scopes != null) {
                    baseClientDetails.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(scopes));
                }
            }else {
                throw new NoSuchClientException("No client with requested id: " + clientId);
            }

            return baseClientDetails;

        }
        catch (EmptyResultDataAccessException e) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }

    }


    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        try {
            OauthClientDetails oauthClientDetails = new OauthClientDetails();
            oauthClientDetails.setClientId(clientDetails.getClientId());
            oauthClientDetails.setClientSecret(clientDetails.getClientSecret());
            oauthClientDetails.setAccessTokenValidity(clientDetails.getAccessTokenValiditySeconds());
            oauthClientDetails.setRefreshTokenValidity(clientDetails.getRefreshTokenValiditySeconds());
            oauthClientDetails.setScope(StringUtils.collectionToCommaDelimitedString(clientDetails
                    .getScope()) );
            oauthClientDetails.setWebServerRedirectUri(clientDetails.getRegisteredRedirectUri().isEmpty() ? clientDetails.getRegisteredRedirectUri().iterator().next():"");

            this.oauthClientDetailsService.save(oauthClientDetails);
        }
        catch (DuplicateKeyException e) {
            throw new ClientAlreadyExistsException("Client already exists: " + clientDetails.getClientId(), e);
        }
    }

    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        this.addClientDetails(clientDetails);
    }

    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        OauthClientDetails oauthClientDetails = this.oauthClientDetailsService.findByClientId(clientId);
        oauthClientDetails.setClientSecret(secret);
        this.oauthClientDetailsService.save(oauthClientDetails);

    }

    public void removeClientDetails(String clientId) throws NoSuchClientException {
        this.oauthClientDetailsService.deleteByClientId(clientId);
    }

    public List<ClientDetails> listClientDetails() {
        List<ClientDetails> clientDetailsList = new ArrayList<>();
        List<OauthClientDetails> list = this.oauthClientDetailsService.findAll();
        for (OauthClientDetails oauthClientDetails : list){
            clientDetailsList.add(copy(oauthClientDetails, new BaseClientDetails()));
        }
        return clientDetailsList;
    }

    ClientDetails copy(OauthClientDetails oauthClientDetails, ClientDetails clientDetails){

        oauthClientDetails.setClientId(clientDetails.getClientId());
        oauthClientDetails.setClientSecret(clientDetails.getClientSecret());
        oauthClientDetails.setAccessTokenValidity(clientDetails.getAccessTokenValiditySeconds());
        oauthClientDetails.setRefreshTokenValidity(clientDetails.getRefreshTokenValiditySeconds());
        oauthClientDetails.setScope(StringUtils.collectionToCommaDelimitedString(clientDetails
                .getScope()) );
        oauthClientDetails.setWebServerRedirectUri(clientDetails.getRegisteredRedirectUri().isEmpty() ? clientDetails.getRegisteredRedirectUri().iterator().next():"");

        return clientDetails;
    }
}
