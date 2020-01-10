package com.springboot.api.appserve.services;

import java.util.List;

import com.springboot.api.appserve.models.User;

public interface IUserService {

	User saveUser(User user);

	User findByUsername(String name);

	List<User> findAllUsers();

}
