package cn.edu.ldxy.tutors.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/20
 * @Modified By:
 * @Decription:
 */

@Getter
@Setter
@Document(collection = "Tutors-Setting")
public class Setting  implements Serializable {

    @Id
    private String id;
    /* 导师制选择总开关： 是否允许导师选择 */
    private boolean enable = true;
}
