package cn.edu.ldxy.tutors.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/19
 * @Modified By:
 * @Decription:
 */
@Setter
@Getter
public class Student implements Serializable {

    private String username;
    private String realName;
    private boolean accept = false;
}
