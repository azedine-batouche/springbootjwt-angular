package com.springboot.api.appserve.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.api.appserve.models.User;


@Service
@Transactional
public class UserDetailServiceImpl  implements UserDetailsService {

	@Autowired
	private IUserService iUserService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		 User user = iUserService.findByUsername(username);
		 
		 if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		 Set<GrantedAuthority> authorites = new HashSet<GrantedAuthority>();  
		 authorites.add(new SimpleGrantedAuthority(user.getRole().name()));
		
		 return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorites) ;
	}
}
