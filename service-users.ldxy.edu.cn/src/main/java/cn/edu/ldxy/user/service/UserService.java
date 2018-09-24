package cn.edu.ldxy.user.service;


import cn.edu.ldxy.commons.service.MongodbBaseService;

import cn.edu.ldxy.user.domain.User;
import cn.edu.ldxy.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService extends MongodbBaseService<User, String, UserRepository> {


	User findByUsername(String username);

	Page<User> findByUsernameLike(String username, Pageable pageable);




    Page<User> findByRole(String role, Pageable pageable);



   boolean changePassword(User users, String password);


}
