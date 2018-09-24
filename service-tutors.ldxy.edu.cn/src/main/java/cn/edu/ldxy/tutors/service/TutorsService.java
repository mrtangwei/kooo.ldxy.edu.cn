package cn.edu.ldxy.tutors.service;


import cn.edu.ldxy.commons.service.MongodbBaseService;
import cn.edu.ldxy.tutors.domain.Tutors;
import cn.edu.ldxy.tutors.domain.adapter.TutorsGroup;
import cn.edu.ldxy.tutors.repository.TutorsRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;

import java.util.List;

public interface TutorsService extends MongodbBaseService<Tutors, String, TutorsRepository> {


    @ApiOperation(value="根据导师ID查询导师辅导对象信息", notes="根据导师ID查询导师辅导对象信息")
    Page<Tutors> findByUsername(String username, Pageable pageable);

    @ApiOperation(value="根据辅导年级、导师ID查询导师辅导对象信息", notes="根据辅导年级、导师ID查询导师辅导对象信息")
    Tutors findByYearAndUsername(String year, String username);

    @ApiOperation(value="根据学年统计导师辅导数据", notes="根据学年统计导师辅导数据")
    GroupByResults<TutorsGroup> statistic(String year, Pageable pageable);

    @ApiOperation(value="根据学年、学生ID查询导师信息", notes="根据学年、学生查询导师信息")
    List<Tutors> getByYearAndStudent(String year,String username);

    boolean acceptStudent(String year, String username, String studentId);
}
