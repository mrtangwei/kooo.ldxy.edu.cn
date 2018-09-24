package cn.edu.ldxy.user.repository;


import cn.edu.ldxy.commons.repository.MongodbBaseRepository;

import cn.edu.ldxy.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository extends MongodbBaseRepository<User, String> {


	User findByUsername(String username);
	Page<User> findByUsernameLike(String username, Pageable pageable);
	Page<User> findByRole(String roleName, Pageable pageable);

}
