package by.htp.devteam.service.impl;

import by.htp.devteam.bean.User;
import by.htp.devteam.bean.dto.ProjectListVo;
import by.htp.devteam.bean.dto.UserListVo;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.UserDao;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.UserService;
import by.htp.devteam.service.util.Encripting;
import by.htp.devteam.service.util.ErrorCodeEnum;
import by.htp.devteam.service.validation.ProjectValidation;
import by.htp.devteam.service.validation.UserValidation;
import by.htp.devteam.util.SettingConstantValue;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static by.htp.devteam.service.util.ConstantValue.*;

public class UserServiceImpl implements UserService{

	UserDao userDao;
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class.getName());
	
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
			logger.info(MSG_LOGGER_FILL_LOGIN_OR_PASSWORD);
			throw new ServiceException(ErrorCodeEnum.VALIDATION, userValidation.getNotValidField());
		}
		
		try {
			user = userDao.fetchByCredentials(login);
			if ( user == null ) {
				logger.info(MSG_LOGGER_USER_NOT_FOUND, login);
				throw new ServiceException(ErrorCodeEnum.NO_SUCH_USER);
			} else if ( !Encripting.isCorrectPassword(password, user.getPassword()) ) {
				logger.info(MSG_LOGGER_USER_INCORRECT_PASSWORD);
				throw new ServiceException(ErrorCodeEnum.INCORRECT_PASSWORD);
			} else {
				user.setPassword(null);
			}
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCodeEnum.APPLICATION);
		}
		
		return user;
	}

	@Override
	public void logout(User user) {
		//logger.info(MSG_LOGGER_USER_LOGOUT, user.getLogin());
	}

	@Override
	public UserListVo fetchAll(String currPage) throws ServiceException {
		if ( currPage == null ) {
			currPage = String.valueOf(SettingConstantValue.START_PAGE);
		}
		
		//ProjectValidation projectValidation = new ProjectValidation();
		if ( !UserValidation.validatePage(currPage) ) {
			logger.info(MSG_LOGGER_PAGE_NUMBER_NOT_FOUND, currPage);
			throw new ServiceException(ErrorCodeEnum.PAGE_NUMBER_NOT_FOUND);
		}
		
		int countPerPage = SettingConstantValue.COUNT_PER_PAGE;
		int currPageValue = Integer.valueOf(currPage);		
		int offset = (currPageValue - 1 ) * countPerPage;
			
		UserListVo userListVo = null;
		try {
			userListVo = userDao.fetchAll(offset, countPerPage);
			
			int countPages = (int) Math.ceil(userListVo.getCountRecords() * 1.0 / countPerPage);
			userListVo.setCountPages(countPages);
			userListVo.setCurrPage(currPageValue);
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCodeEnum.APPLICATION);
		}

		return userListVo;
	}

}
