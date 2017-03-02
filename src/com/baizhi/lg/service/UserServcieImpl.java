package com.baizhi.lg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baizhi.lg.dao.UserDAO;
import com.baizhi.lg.entity.User;
@Service("userService")
@Transactional
public class UserServcieImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	@Override
	public List<User> getAllUser() {
		return userDAO.queryUserAll();
	}

}
