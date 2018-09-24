package cn.edu.ldxy.jwt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private FeignUserService feignUserService;

  
  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {

    //服务端提供JSON格式对象
	JSONObject user = JSON.parseObject(this.feignUserService.get(username));

	//if(user == null) throw new NoUserExistException(username);
	Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();
	List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(
			dbAuthsSet);

	SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
			user.getString("Role"));

	dbAuths.add(authority);

	return new User(username, user.getString("password"), user.getBoolean("enable"), true,
			true, true, dbAuths);
  }

}