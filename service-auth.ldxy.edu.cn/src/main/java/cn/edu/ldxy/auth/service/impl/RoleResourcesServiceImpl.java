package cn.edu.ldxy.auth.service.impl;


import cn.edu.ldxy.auth.domain.RoleResources;
import cn.edu.ldxy.auth.repository.RoleResourcesRepository;
import cn.edu.ldxy.auth.service.RoleResourcesService;
import cn.edu.ldxy.commons.service.impl.MongodbBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("roleResourcesService")
public class RoleResourcesServiceImpl extends
		MongodbBaseServiceImpl<RoleResources, String, RoleResourcesRepository>
		implements RoleResourcesService {

	@Autowired(required = true)
	private RoleResourcesRepository repository;

	@Override
	public List<RoleResources> findByRole(String role) {
		// TODO Auto-generated method stub
		return this.repository.findByRole(role);
	}

}
