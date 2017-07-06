package by.htp.devteam.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileUploadException;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;
import by.htp.devteam.bean.dto.OrderVo;
import by.htp.devteam.bean.dto.OrderListVo;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.OrderDao;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.util.ErrorCodeEnum;
import by.htp.devteam.service.util.UploadFile;
import by.htp.devteam.service.util.Validator;
import by.htp.devteam.service.validation.OrderValidation;
import by.htp.devteam.util.SettingConstantValue;

public class OrderServiceImpl implements OrderService{

	private OrderDao orderDao;
	
	public OrderServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		orderDao = daoFactory.getOrderDao();
	}
	
	@Override
	public OrderListVo getNewOrders(String currPage) throws ServiceException{
		int countPerPage = SettingConstantValue.COUNT_PER_PAGE;
		int currPageValue = 0;
		
		currPageValue = ( currPage == null 
					  ? SettingConstantValue.START_PAGE 
					  : Integer.valueOf(currPage) );
		
		if ( currPageValue == 0 )
			throw new ServiceException("page not found");
		
		int offset = (currPageValue - 1 ) * countPerPage;
			
		OrderListVo orderListVo = new OrderListVo();
		try {
			orderListVo = orderDao.getNewOrders(offset, countPerPage);
			int countPages = (int) Math.ceil(orderListVo.getCountRecords() * 1.0 / countPerPage);
			orderListVo.setCountPages(countPages);
			orderListVo.setCurrPage(currPageValue);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("service error", e);
		}
	
		return orderListVo;
	}
	
	@Override
	public List<Order> geOrdersByCustomer(Customer customer) throws ServiceException{
		List<Order> orders = new ArrayList<Order>();
		try {
			orders = orderDao.getByCustomer(customer);
		} catch (DaoException e) {
			throw new ServiceException("error", e);
		}
		
		return orders;
	}

	@Override
	public OrderVo add(Customer customer, String title, String description, Part specification, String dateStart, String dateFinish,
			String[] workIds, Map<String, String> qualificationsIdsAndCount, String applicationPath) throws ServiceException {
		
		UploadFile uploadFile = new UploadFile(applicationPath);
		String specificationFileName = uploadFile.getFileName(specification);
		
		OrderValidation orderValidation = new OrderValidation();
		orderValidation.validate(title, description, specificationFileName, dateStart, dateFinish, workIds, qualificationsIdsAndCount);
		
		if ( !orderValidation.isValid() ) {
			throw new ServiceException(ErrorCodeEnum.VALIDATION_ERROR, orderValidation.getNotValidField());
		} 
		
		try {
			uploadFile.upload(specification);
		} catch (FileUploadException e1) {
			throw new ServiceException(ErrorCodeEnum.FILE_UPLOAD_ERROR);
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
		//order.setSpecification(specification);
		OrderVo orderVo = new OrderVo();
		orderVo.setOrder(order);
		orderVo.setWorks(prepareWorks(workIds));
		orderVo.setQualifications(prepareQualifications(qualificationsIdsAndCount));
		
		try {
			orderVo = orderDao.add(orderVo);
		} catch (DaoException e) {
			////   Logger
			System.out.println(e.getMessage()); // message from DAO
			e.printStackTrace();
			try {
				uploadFile.delete(specificationFileName);
			} catch (FileUploadException e1) {
				e1.printStackTrace();
				////Loger
			}

			throw new ServiceException(ErrorCodeEnum.APPLICATION_ERROR);
		}	
		
		return orderVo;
	}

	@Override
	public OrderVo getOrderById(String orderId) throws ServiceException {
		if ( !Validator.isLong(orderId) )
			throw new ServiceException("id is not valid");
		
		OrderVo orderVo = null;
		try {
			orderVo = orderDao.getById(Long.valueOf(orderId));
		} catch (DaoException e) {
			e.printStackTrace();
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
	


	
}
