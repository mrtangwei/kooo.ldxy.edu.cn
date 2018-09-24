package cn.edu.ldxy.auth.repository;


import cn.edu.ldxy.auth.domain.RoleResources;
import cn.edu.ldxy.commons.repository.MongodbBaseRepository;

import java.util.List;

public interface RoleResourcesRepository extends
		MongodbBaseRepository<RoleResources, String> {

	List<RoleResources> findByRole(String role);

}
