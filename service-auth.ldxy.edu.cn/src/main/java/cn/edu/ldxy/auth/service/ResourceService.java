package cn.edu.ldxy.auth.service;


import cn.edu.ldxy.auth.domain.Resource;
import cn.edu.ldxy.auth.repository.ResourceRepository;
import cn.edu.ldxy.commons.service.MongodbBaseService;

import java.util.List;

public interface ResourceService extends
		MongodbBaseService<Resource, String, ResourceRepository> {

	// Page<Resource> findByPage(int num);

	Resource findById(String id);

	List<Resource> findByName(String name);

    Resource findByUrl(String url);

}
