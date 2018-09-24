package cn.edu.ldxy.tutors.service.impl;

import cn.edu.ldxy.commons.service.impl.MongodbBaseServiceImpl;
import cn.edu.ldxy.tutors.domain.Setting;
import cn.edu.ldxy.tutors.repository.SettingRepository;
import cn.edu.ldxy.tutors.service.SettingService;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl extends MongodbBaseServiceImpl<Setting, String, SettingRepository>
							 implements SettingService {



}
