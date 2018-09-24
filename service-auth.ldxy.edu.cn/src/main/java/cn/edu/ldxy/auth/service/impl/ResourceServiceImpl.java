package cn.edu.ldxy.auth.service.impl;


import cn.edu.ldxy.auth.domain.Resource;
import cn.edu.ldxy.auth.repository.ResourceRepository;
import cn.edu.ldxy.auth.service.ResourceService;
import cn.edu.ldxy.commons.service.impl.MongodbBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ResourceServiceImpl extends MongodbBaseServiceImpl<Resource, String, ResourceRepository> implements
		ResourceService {

	@Autowired(required = true)
	private ResourceRepository repository;


	public Resource findById(String id) {
		return this.repository.findById(id).get();
	}

	public List<Resource> findByName(String name) {
		return this.repository.findByName(name);
	}

    public Resource findByUrl(String url){
        return this.repository.findByUrl(url);
    }

}
