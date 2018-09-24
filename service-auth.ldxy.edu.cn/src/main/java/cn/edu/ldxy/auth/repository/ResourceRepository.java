package cn.edu.ldxy.auth.repository;


import cn.edu.ldxy.auth.domain.Resource;
import cn.edu.ldxy.commons.repository.MongodbBaseRepository;

import java.util.List;

public interface ResourceRepository extends MongodbBaseRepository<Resource, String> {

	List<Resource> findByName(String name);

	// List<Resources> findAll(Class<Resources> c);
    Resource findByUrl(String url);

}
