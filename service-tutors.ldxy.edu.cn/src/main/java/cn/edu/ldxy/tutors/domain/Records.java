package cn.edu.ldxy.tutors.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/19
 * @Modified By:
 * @Decription: 工作记录
 */
@Getter
@Setter
@Document(collection = "Tutors-Records")
public class Records implements Serializable {

    @Id
    private String id;
    /* 导师ID */
    private String username;
    /* 导师姓名 */
    private String realName;
    /* 工作主题 */
    private String subject;
    /* 辅导对象 */
    private List<Student> student;
    /* 辅导时间 */
    private Date date;
    /* 辅导内容 */
    private String content;


}
