package cn.edu.ldxy.tutors.service;


import cn.edu.ldxy.commons.service.MongodbBaseService;
 import cn.edu.ldxy.tutors.domain.Setting;
  import cn.edu.ldxy.tutors.repository.SettingRepository;
import io.swagger.annotations.Api;


@Api(value = "系统设置", description = "系统设置，系统总开关")
public interface SettingService extends MongodbBaseService<Setting, String, SettingRepository> {




}
