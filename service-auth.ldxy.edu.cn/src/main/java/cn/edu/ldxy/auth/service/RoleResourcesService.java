package cn.edu.ldxy.auth.service;


import cn.edu.ldxy.auth.domain.RoleResources;
import cn.edu.ldxy.auth.repository.RoleResourcesRepository;
import cn.edu.ldxy.commons.service.MongodbBaseService;

import java.util.List;

public interface RoleResourcesService extends
		MongodbBaseService<RoleResources, String, RoleResourcesRepository> {

	List<RoleResources> findByRole(String role);

}
