package cn.edu.ldxy.tutors.controller;

import cn.edu.ldxy.tutors.domain.Setting;
import cn.edu.ldxy.tutors.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/20
 * @Modified By:
 * @Decription:
 */

@RestController
public class SettingController {


    @Autowired
    private SettingService settingService;

    @PostMapping(value = "/security/tutors/setting/add/")
    public boolean save(@RequestBody Setting setting){
        this.settingService.save(setting);
        return true;
    }

}
