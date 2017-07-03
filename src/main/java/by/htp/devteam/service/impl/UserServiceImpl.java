package by.htp.devteam.service.impl;

import by.htp.devteam.bean.User;
import by.htp.devteam.dao.DaoException;
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
		User user = null;
		try {
			user = userDao.fetchByCredentials(login, password);
			if ( user == null ) {
				throw new ServiceException("Invalid credentials");
			}
		} catch (DaoException e) {
			throw new ServiceException("service error", e);
		}
		
		return user;
	}

}
