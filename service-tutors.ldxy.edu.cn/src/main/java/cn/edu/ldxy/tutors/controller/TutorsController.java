package cn.edu.ldxy.tutors.controller;

import cn.edu.ldxy.api.FeignUserService;
import cn.edu.ldxy.api.FeignWechatService;
import cn.edu.ldxy.tutors.domain.Student;
import cn.edu.ldxy.tutors.domain.Tutors;
import cn.edu.ldxy.tutors.service.TutorsService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/19
 * @Modified By:
 * @Decription:
 */
@RestController
public class TutorsController {

    @Autowired
    private FeignUserService feignUserService;
    @Autowired
    private FeignWechatService feignWechatService;

    @Autowired
    private TutorsService tutorsService;

/*
    @Autowired
    private MongoTemplate mongoTemplate;

*/



    @GetMapping(value = "/feign-tutors-service/tutors/{username}")
    public Page<Tutors> get(@PathVariable("username")String username, Pageable pageable){
        return this.tutorsService.findByUsername(username, pageable);
    }

    @GetMapping(value = "/feign-tutors-service/tutors/teacher/{year}/{username}")
    public Tutors get(@PathVariable("year")String year,
                            @PathVariable("username")String username){
        return this.tutorsService.findByYearAndUsername(year, username);
    }

    /* 根据学年、学生ID查询导师信息 */
    @GetMapping(value = "/feign-tutors-service/tutors/student/{year}/{username}")
    public List<Tutors> getByStudent(@PathVariable("year")String year,
                                     @PathVariable("username")String username){
        return this.tutorsService.getByYearAndStudent(year,username);
    }


    /** 接受学生studentId 申请 **/
    @GetMapping(value = "/feign-tutors-service/tutors/accept/{year}/{username}/{studentId}/")
    public boolean save(@PathVariable("year")String year,
                       @PathVariable("username")String username,
                       @PathVariable("studentId")String studentId){
        //本段没有测试
        //导师接受学生申请后，推送微信消息通知
        JSONObject wechatMessage = new JSONObject();
        wechatMessage.put("title","导师接受通知");
        wechatMessage.put("sender","导师制微服务平台");
        JSONArray jsonArray = new JSONArray();
        JSONObject student = new JSONObject();
        student.put("username", username);
        student.put("weChatId", this.feignUserService.get(username).getString("openId"));
        jsonArray.add(student);
        wechatMessage.put("students",jsonArray);
        wechatMessage.put("content","恭喜您，导师已接收您的选择！您可以在学校导师制选择平台查看具体信息，感谢使用信息中心研发的微服务系统。");
        this.feignWechatService.post(wechatMessage);

        return this.tutorsService.acceptStudent(year,username,studentId);
    }

    /* 学生申请导师 */
    @GetMapping(value = "/feign-tutors-service/tutors/apply/{year}/{username}/{studentId}/")
    public boolean apply(@PathVariable("year")String year,
                         @PathVariable("username")String username,
                         @PathVariable("studentId")String studentId){
        Tutors tutors = this.tutorsService.findByYearAndUsername(year, username);
        if (tutors != null){
            //获取学生基本信息

                Student student = new Student();
                student.setUsername(studentId);
                //student.setRealName("邱少波");
                tutors.getStudents().add(student);

        }else {
            tutors = new Tutors();
            tutors.setUsername(username);
            //t.setRealName("李存勖");
            tutors.setYear(year);
            List<Student> studentList = new ArrayList<>();

            Student student = new Student();
            student.setUsername(studentId);
            //student.setRealName("邱少波");
            studentList.add(student);
            tutors.setStudents(studentList);

        }
        this.tutorsService.save(tutors);

        return true;
    }

    @PostMapping(value = "/feign-tutors-service/tutors/document/add/")
    public Tutors save(@RequestBody Tutors tutors){
        return this.tutorsService.save(tutors);
    }



}
