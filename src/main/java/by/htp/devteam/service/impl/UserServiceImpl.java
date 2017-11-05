package by.htp.devteam.service.impl;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.User;
import by.htp.devteam.bean.UserRole;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.bean.vo.UserVo;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.UserDao;
import by.htp.devteam.service.EmployeeService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.service.UserService;
import by.htp.devteam.service.util.Encrypting;
import by.htp.devteam.service.util.ErrorCode;
import by.htp.devteam.service.validation.PagingValidation;
import by.htp.devteam.service.validation.UserValidation;
import by.htp.devteam.util.ConfigProperty;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static by.htp.devteam.service.util.ConstantValue.*;

import java.sql.Connection;

public final class UserServiceImpl implements UserService{

	/** Logger */
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class.getName());
	
	/** DAO object */
	private UserDao userDao;
	
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
			throw new ServiceException(ErrorCode.VALIDATION, userValidation.getNotValidField());
		}
		
		logger.info(MSG_LOGGER_USER_NOT_FOUND, Encrypting.getCode(password));
		
		try {
			user = userDao.fetchByCredentials(login);
			if ( user == null ) {
				logger.info(MSG_LOGGER_USER_NOT_FOUND, login);
				throw new ServiceException(ErrorCode.NO_SUCH_USER);
			} else if ( !Encrypting.isCorrectPassword(password, user.getPassword()) ) {
				logger.info(MSG_LOGGER_USER_INCORRECT_PASSWORD);
				throw new ServiceException(ErrorCode.INCORRECT_PASSWORD);
			} else {
				user.setPassword(null);
			}
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}
		
		return user;
	}

	@Override
	public PagingVo<UserVo> fetchAll(String currPage) throws ServiceException {
		if ( currPage == null ) {
			currPage = ConfigProperty.INSTANCE.getStringValue(CONFIG_PAGE_START_PAGE);
		}
		
		if ( !PagingValidation.getInstance().validatePage(currPage) ) {
			logger.info(MSG_LOGGER_PAGE_NUMBER_NOT_FOUND, currPage);
			throw new ServiceException(ErrorCode.PAGE_NUMBER_NOT_FOUND);
		}
		
		int countPerPage = ConfigProperty.INSTANCE.getIntValue(CONFIG_PAGE_COUNT_PER_PAGE);
		int currPageValue = Integer.valueOf(currPage);		
		int offset = (currPageValue - 1 ) * countPerPage;
			
		PagingVo<UserVo> pagingVo = null;
		try {
			pagingVo = userDao.fetchAll(offset, countPerPage);
			
			int countPages = (int) Math.ceil(pagingVo.getCountAllRecords() * 1.0 / countPerPage);
			pagingVo.setCountPages(countPages);
			pagingVo.setCurrPage(currPageValue);
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}

		return pagingVo;
	}

	@Override
	public User add(String login, String password, String role, Employee employee) throws ServiceException {
		UserValidation userValidation = new UserValidation();
		userValidation.validate(login, password, role, employee);
		
		if ( !userValidation.isValid() ) {
			logger.info(MSG_LOGGER_USER_ADD_INCORRECT_FIELD);
			throw new ServiceException(ErrorCode.VALIDATION, userValidation.getNotValidField());
		}
		
		User user = new User();
		user.setLogin(login);
		user.setPassword(Encrypting.getCode(password));
		user.setRole(UserRole.valueOf(role));
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		EmployeeService employeeService = serviceFactory.getEmployeeService();
		
		Connection connection = null;
		try {
			connection = userDao.startTransaction();
			boolean isExistUserForEmployee = employeeService.isExistUserForEmployee(connection, employee);
			if ( isExistUserForEmployee == false ) {
				user = userDao.add(connection, user);
				employeeService.setUserForEmployee(connection, employee, user);
				commitTransaction(connection);
			} else {
				rollbackTransaction(connection);
				logger.info(MSG_LOGGER_EMPLOYEE_USER_ALREADY_EXIST);
				throw new ServiceException(ErrorCode.USER_FOR_EMPLOYEE_ALREADY_EXIST);
			}
		} catch ( DaoException e ) {
			rollbackTransaction(connection);
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}
		
		return user;
	}

	/*
	 * rollback transaction
	 */
	private void rollbackTransaction(Connection connection) throws ServiceException {
		try {
			userDao.rollbackTransaction(connection);
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}
	}
	
	/*
	 * Commit transaction
	 */
	private void commitTransaction(Connection connection) throws DaoException {
		userDao.commitTransaction(connection);
	}

	
}
