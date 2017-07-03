package by.htp.devteam.service.impl;

import by.htp.devteam.bean.User;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.UserDao;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.UserService;
import by.htp.devteam.util.Encripting;
import by.htp.devteam.util.Validator;

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
		
		if ( Validator.isEmpty(login) || Validator.isEmpty(password) )
			throw new ServiceException("fill login or password");
		
		try {
			user = userDao.fetchByCredentials(login);
			if ( user == null ) {
				throw new ServiceException("No such user");
			} else if ( !Encripting.isCorrectPassword(password, user.getPassword()) ) {
				throw new ServiceException("Password is not correct");
			} else {
				user.setPassword(null);
			}
		} catch (DaoException e) {
			throw new ServiceException("service error", e);
		}
		
		return user;
	}

}
