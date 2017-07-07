package by.htp.devteam.service.impl;

import by.htp.devteam.bean.User;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.UserDao;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.UserService;
import by.htp.devteam.service.util.Encripting;
import by.htp.devteam.service.util.ErrorCodeEnum;
import by.htp.devteam.service.util.Validator;
import by.htp.devteam.service.validation.OrderValidation;
import by.htp.devteam.service.validation.UserValidation;

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
		
		UserValidation userValidation = new UserValidation();
		userValidation.validate(login, password);
		
		if ( !userValidation.isValid() ) {
			throw new ServiceException(ErrorCodeEnum.VALIDATION, userValidation.getNotValidField());
		}
		
		try {
			user = userDao.fetchByCredentials(login);
			if ( user == null ) {
				// Logger
				throw new ServiceException(ErrorCodeEnum.NO_SUCH_USER);
			} else if ( !Encripting.isCorrectPassword(password, user.getPassword()) ) {
				/// Logger
				throw new ServiceException(ErrorCodeEnum.INCORRECT_PASSWORD);
			} else {
				user.setPassword(null);
			}
		} catch ( DaoException e ) {
			/// Logger
			throw new ServiceException(ErrorCodeEnum.APPLICATION);
		}
		
		return user;
	}

}
