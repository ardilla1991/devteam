package by.htp.devteam.service.impl;

import by.htp.devteam.bean.User;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.UserDao;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.UserService;

public class UserServiceImpl implements UserService{

	UserDao userDao;
	
	public UserServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		userDao = daoFactory.getUserDao();
	}
	
	@Override
	public User authorise(String login, String password) throws ServiceException {
		User user = userDao.fetchByCredentials(login, password);
		System.out.println("user");
		if ( user == null ) {
			throw new ServiceException("Invalid credentials");
		}
		
		return user;
	}

}
