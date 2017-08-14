package by.htp.devteam.service.impl;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;
import by.htp.devteam.bean.vo.OrderVo;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.ObjectNotFoundException;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.OrderDao;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.util.ErrorCode;
import by.htp.devteam.service.util.FileUploadException;
import by.htp.devteam.service.util.UploadFile;
import by.htp.devteam.service.validation.OrderValidation;
import by.htp.devteam.service.validation.PagingValidation;
import by.htp.devteam.util.ConfigProperty;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static by.htp.devteam.service.util.ConstantValue.*;

public final class OrderServiceImpl implements OrderService{

	/** Logger */
	private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class.getName());
	
	/** DAO object */
	private OrderDao orderDao;
	
	public OrderServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		orderDao = daoFactory.getOrderDao();
	}
	
	@Override
	public PagingVo<Order> getNewOrders(String currPage) throws ServiceException{
		
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
			
		PagingVo<Order> pagingVo = new PagingVo<Order>();
		try {
			pagingVo = orderDao.getNewOrders(offset, countPerPage);
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
	public List<Order> geOrdersByCustomer(Customer customer) throws ServiceException{
		List<Order> orders = new ArrayList<Order>();
		try {
			orders = orderDao.getByCustomer(customer);
		} catch ( DaoException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}
		
		return orders;
	}

	@Override
	public OrderVo add(Customer customer, String title, String description, Part specification, String dateStart, String dateFinish,
			String[] workIds, Map<String, String> qualificationsIdsAndCount) throws ServiceException {
		
		UploadFile uploadFile = UploadFile.getInstance();
		String specificationFileName = uploadFile.getFileName(specification);
		
		OrderValidation orderValidation = new OrderValidation();
		orderValidation.validate(title, description, specificationFileName, dateStart, dateFinish, workIds, qualificationsIdsAndCount);
		
		if ( !orderValidation.isValid() ) {
			logger.info(MSG_LOGGER_ORDER_ADD_INCORRECT_FIELD);
			throw new ServiceException(ErrorCode.VALIDATION, orderValidation.getNotValidField());
		} 
		
		try {
			uploadFile.upload(specification, specificationFileName);
		} catch ( FileUploadException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.FILE_UPLOAD);
		}
		
		Order order = new Order();
		order.setTitle(title);
		order.setDescription(description);
		java.util.Date utilDate = new java.util.Date();
	    Date sqlDate = new Date(utilDate.getTime());
		order.setDateCreated(sqlDate);
		order.setDateStart(Date.valueOf(dateStart));
		order.setDateFinish(Date.valueOf(dateFinish));
		order.setCustomer(customer);
		order.setSpecification(specificationFileName);
		OrderVo orderVo = new OrderVo();
		orderVo.setOrder(order);
		orderVo.setWorks(prepareWorks(workIds));
		orderVo.setQualifications(prepareQualifications(qualificationsIdsAndCount));
		
		try {
			orderVo = orderDao.add(orderVo);
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			try {
				uploadFile.delete(specificationFileName);
			} catch (FileUploadException e1) {
				logger.error(e.getMessage(), e);
				throw new ServiceException(ErrorCode.FILE_DELETE);
			}

			throw new ServiceException(ErrorCode.APPLICATION);
		}	
		
		return orderVo;
	}

	@Override
	public OrderVo getById(String orderId) throws ServiceException, ObjectNotFoundException {

		OrderValidation orderValidation = new OrderValidation();
		if ( !orderValidation.validateId(orderId)) {
			logger.info(MSG_LOGGER_ORDER_VIEW_INCORRECT_ID, orderId);
			throw new ServiceException(ErrorCode.VALIDATION_ID);
		} 
		
		OrderVo orderVo = null;
		try {
			orderVo = orderDao.getById(Long.valueOf(orderId));
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		} catch ( NullPointerException e ) {
			logger.info(MSG_LOGGER_ORDER_VIEW_NOT_EXIST_ID, orderId);
			throw new ServiceException(ErrorCode.VALIDATION_ID);
		} catch (ObjectNotFoundException e) {
			logger.info(e.getMessage());
			throw new ObjectNotFoundException(e.getMessage());
		}
		
		return orderVo;
	}
	
	private List<Work> prepareWorks(String[] worksIds) {
		List<Work> works = new ArrayList<Work>();
		for ( int i = 0; i < worksIds.length; i++ ) {
			Work work = new Work();
			work.setId(Long.valueOf(worksIds[i]));
			
			works.add(work);
		}
		
		return works;
	}
	
	private HashMap<Qualification, Integer> prepareQualifications(Map<String, String> qualifications) {
		
		HashMap<Qualification, Integer> qualificationsList = new HashMap<Qualification, Integer>();
		Iterator it = qualifications.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			Qualification qualification = new Qualification();
			qualification.setId(Long.valueOf((String) pair.getKey()));
			
			qualificationsList.put(qualification, Integer.valueOf((String) pair.getValue()));
		}
		return qualificationsList;
	}

	@Override
	public void setPriceAndDateProcessing(Connection connection, Order order) 
			throws ServiceException {
		try {
			orderDao.setPriceAndDateProcessing(connection, order);
		} catch ( DaoException e ) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ErrorCode.APPLICATION);
		}
		
	}
	
}
