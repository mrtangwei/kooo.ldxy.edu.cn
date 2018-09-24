package cn.edu.ldxy.wechat.domain;



import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;




@Setter
@Getter
@Document(collection = "Message")
public class Message implements Serializable {

    @Id
    private String id;
    /* 消息标题 */
    private String title;

    /* 消息发送者 */
    private String sender;
    
    /* 接受消息的用户 wechatID */
    private List<User> userList;

    /* 消息内容 */
    private String content;
    /* 消息发送时间 */
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
    private Date date = new Date(System.currentTimeMillis());
}

