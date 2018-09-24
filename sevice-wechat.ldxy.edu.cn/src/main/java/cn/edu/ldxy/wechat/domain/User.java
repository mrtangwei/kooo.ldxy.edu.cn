package cn.edu.ldxy.wechat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/19
 * @Modified By:
 * @Decription:
 */
@Getter
@Setter
public class User implements Serializable {

    //private String id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("weChatId")
    private String weChatId;

    public User(@JsonProperty("username")String username,
                @JsonProperty("weChatId")String weChatId){
        this.username = username;
        this.weChatId = weChatId;
    }
}
