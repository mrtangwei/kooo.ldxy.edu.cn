package cn.edu.ldxy.user.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Document(collection = "User")
public class User implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = -9209457016175618546L;

	@Id
	private String id;

	/**
	 * 登录用户名，陇东学院统一编号
	 *
	 */
    @Indexed(unique = true)
    private String username;
    /* 用户密码 */
    private String password;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 证件类型
     * 01： 身份证
     */
    private String cardType;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 政治面貌
     * 01： 中国共产党
     */
    private String party;
    /**
     * 政治面貌名称
     */
    private String partyName;

    /**
     * 职工类别
     * 1：在编， 2：
     */
    private String type;
    /**
     * 职工类别名称
     */
    private String typeName;
    /**
     * 身份代码
     *
     */
    private String identity;
    /**
     * 身份名称
     */
    private String identityName;
    /**
     * 用户状态， 在职、离职、退休、毕业、休学、请假、在校
     *
     */
    private String state;
    /**
     * 是否导师制导师
     */
    private boolean isTutors = false;
    /**
     * 导师属性: 导师、优秀导师
     */
    private String tutors = "";
    /* 受聘导师时间 */
    private Date[] tutorsDate;

    /**
     * 微信openId
     */
    private String weChatOpenId;
    /** 角色： 教职工、学生 以及各种管理角色*/
    private String role = "ROLE_USER";
    private String roleName;

    /** zf 密码**/
    private String zfPassword;
    private boolean zhengFang = false;
    private boolean enable = true;

    /** weixin openId **/
    private String openId;
    private boolean weChat;


    /**
     * 注册日期
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastLoginDate = new Date();
    private String lastLoginIp;


    /**
     * 性别
     */
    private String sex;

    /**
     * 部门编码
     */
    private String departmentId;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 专业
     */
    private String major;
    /** 学历 **/
    private String level;
    /** 学制 **/
    private String levelYear;
    private String className;
    /** 师范生、非师范、三校生等**/
    private String nature;
    /** 个人联系电话 **/
    private String tel;

    /** 公寓编号**/
    private String roomCode;



}