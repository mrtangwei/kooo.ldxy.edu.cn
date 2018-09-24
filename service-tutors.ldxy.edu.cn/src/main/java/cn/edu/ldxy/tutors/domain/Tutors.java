package cn.edu.ldxy.tutors.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/19
 * @Modified By:
 * @Decription:
 */
@Setter
@Getter
@Document(collection = "Tutors-Document")
public class Tutors implements Serializable {

    @Id
    private String id;

    /* 导师ID（统一认证ID） */
    private String username;
    /* 导师姓名 */
    private String realName;
    /* 辅导年级 */
    private String year;

    private List<Student> students =  new ArrayList<>();
/*    *//* 学生id *//*
    private String studentId;
    *//* 学生姓名 *//*
    private String studentName;
    *//* 是否确认接受 *//*
    private boolean accept = false;*/

    /* 是否完成双选工作 */
    private boolean enable;



}
