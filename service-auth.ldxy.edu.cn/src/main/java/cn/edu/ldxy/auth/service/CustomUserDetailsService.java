package cn.edu.ldxy.auth.service;

import cn.edu.ldxy.api.FeignUserService;
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


		JSONObject user = this.feignUserService.get(username);
		if(user == null) throw new UsernameNotFoundException(username);
		Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();
		List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(
				dbAuthsSet);

		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
				user.getString("role"));

		dbAuths.add(authority);


		return new User(username, user.getString("password"), user.getBoolean("enable"), true,
				true, true, dbAuths);
  }

}