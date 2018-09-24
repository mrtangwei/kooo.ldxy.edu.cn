package cn.edu.ldxy.auth.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/16
 * @Modified By:
 * @Decription:
 */

@Getter
@Setter
@Document(collection = "OauthClientDetails")
public class OauthClientDetails implements Serializable {

    @Id
    private String id;
    private String clientId;
    private String resourceIds;
    private String clientSecret;
    private String scope;
    private String authorizedGrantTypes;
    private String webServerRedirectUri;
    private String authorities;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();


    private String autoApprove;


    public void setAdditionalInformation(Map<String, ?> additionalInformation) {
        this.additionalInformation = new LinkedHashMap<String, Object>(
                additionalInformation);
    }

    @org.codehaus.jackson.annotate.JsonAnyGetter
    @com.fasterxml.jackson.annotation.JsonAnyGetter
    public Map<String, Object> getAdditionalInformation() {
        return Collections.unmodifiableMap(this.additionalInformation);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OauthClientDetails that = (OauthClientDetails) o;
        return Objects.equals(clientId, that.clientId) &&
                Objects.equals(resourceIds, that.resourceIds) &&
                Objects.equals(clientSecret, that.clientSecret) &&
                Objects.equals(scope, that.scope) &&
                Objects.equals(authorizedGrantTypes, that.authorizedGrantTypes) &&
                Objects.equals(webServerRedirectUri, that.webServerRedirectUri) &&
                Objects.equals(authorities, that.authorities) &&
                Objects.equals(accessTokenValidity, that.accessTokenValidity) &&
                Objects.equals(refreshTokenValidity, that.refreshTokenValidity) &&
                Objects.equals(additionalInformation, that.additionalInformation) &&
                Objects.equals(autoApprove, that.autoApprove);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, resourceIds, clientSecret, scope, authorizedGrantTypes, webServerRedirectUri, authorities, accessTokenValidity, refreshTokenValidity, additionalInformation, autoApprove);
    }
}
