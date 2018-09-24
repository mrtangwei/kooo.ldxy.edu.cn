package cn.edu.ldxy.department.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/17
 * @Modified By:
 * @Decription:
 */

@Getter
@Setter
@Document(collection = "Department")
public class Department implements Serializable {

    @Id
    private String id;

    /**
     * 单位代码
     */
    private String code;
    /**
     * 单位名称
     */
    private String name;
    /**
     * 单位类别代码
     */
    private String type;
    /**
     * 单位类别名称
     */
    private String typeName;
    /**
     * 上级部门代码
     */
    private String parentCode;
    /**
     * 层级
     */
    private String level;



}
