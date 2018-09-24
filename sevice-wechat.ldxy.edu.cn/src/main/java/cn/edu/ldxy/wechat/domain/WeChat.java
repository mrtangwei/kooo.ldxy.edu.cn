package cn.edu.ldxy.wechat.domain;

import lombok.Getter;
import lombok.Setter;
 import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kooo on 2017/11/21.
 */
@Getter
@Setter
@Document(collection = "WeChat")
public class WeChat  implements Serializable {

    @Id
    private String id;

    private String account;

    private String Token;

    private String qrCode;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date endDate;


}
