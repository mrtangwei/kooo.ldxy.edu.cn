package cn.edu.ldxy.user.service.impl;

import cn.edu.ldxy.commons.service.impl.MongodbBaseServiceImpl;

import cn.edu.ldxy.user.domain.User;
import cn.edu.ldxy.user.repository.UserRepository;

import cn.edu.ldxy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends MongodbBaseServiceImpl<User, String, UserRepository>
							 implements UserService {

	@Autowired
	private UserRepository repository;


	public User findByUsername(String username) {

		return this.repository.findByUsername(username);
	}


	public Page<User> findByUsernameLike(String username, Pageable pageable) {
		return this.repository.findByUsernameLike(username, pageable);
	}


    public Page<User> findByRole(String role, Pageable pageable){
        return  this.repository.findByRole(role,pageable);
    }


	public boolean changePassword(User User, String password){
		User.setPassword(new BCryptPasswordEncoder().encode(User.getPassword()));
		this.repository.save(User);
		return true;
	}



}
