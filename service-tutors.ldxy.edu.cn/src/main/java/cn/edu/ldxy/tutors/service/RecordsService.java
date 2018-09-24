package cn.edu.ldxy.tutors.service;


import cn.edu.ldxy.commons.service.MongodbBaseService;
import cn.edu.ldxy.tutors.domain.Records;
import cn.edu.ldxy.tutors.domain.Student;
import cn.edu.ldxy.tutors.repository.RecordsRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(value = "导师工作记录", description = "导师记录工作，学生查询辅导记录")
public interface RecordsService extends MongodbBaseService<Records, String, RecordsRepository> {


    //option的value的内容是这个method的描述，notes是详细描述，response是最终返回的json model。其他可以忽略
    @ApiOperation(value="根据导师ID查询工作记录", notes="根据导师ID查询工作记录")
    Page<Records> findByUsername(String username, Pageable pageable);
    @ApiOperation(value="根据学生信息查询工作记录", notes="根据学生信息查询工作记录")
    Page<Records> findByUser(Student user, Pageable pageable);



}
