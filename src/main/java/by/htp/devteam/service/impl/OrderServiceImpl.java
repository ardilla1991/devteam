package by.htp.devteam.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;
import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.bean.dto.OrderListDto;
import by.htp.devteam.dao.DaoException;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.OrderDao;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.util.SettingConstantValue;
import by.htp.devteam.util.Validator;

public class OrderServiceImpl implements OrderService{

	private OrderDao orderDao;
	
	public OrderServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		orderDao = daoFactory.getOrderDao();
	}
	
	@Override
	public OrderListDto getNewOrders(String currPage) throws ServiceException{
		int countPerPage = SettingConstantValue.COUNT_PER_PAGE;
		int currPageValue = 0;
		
		currPageValue = ( currPage == null 
					  ? SettingConstantValue.START_PAGE 
					  : Integer.valueOf(currPage) );
		
		if ( currPageValue == 0 )
			throw new ServiceException("page not found");
		
		int offset = (currPageValue - 1 ) * countPerPage;
			
		OrderListDto orderDto = orderDao.getNewOrders(offset, countPerPage);

		int countPages = (int) Math.ceil(orderDto.getCountRecords() * 1.0 / countPerPage);
		orderDto.setCountPages(countPages);
		orderDto.setCurrPage(currPageValue);
		
		return orderDto;
	}
	
	@Override
	public List<Order> geOrdersByCustomer(Customer customer) {

		return orderDao.getByCustomer(customer);
	}

	@Override
	public Order add(Customer customer, String title, String description, String specification, String dateStart, String dateFinish,
			String[] workIds, Map<String, String> qualificationsIdsAndCount) throws ServiceException {
		 Calendar c1 = Calendar.getInstance();
		 java.util.Date now = c1.getTime();
		
		if ( Validator.isEmpty(title) || workIds.length == 0
				|| qualificationsIdsAndCount.size() == 0
				|| !Validator.isDate(dateStart)
				|| !Validator.isDate(dateFinish)  ) {
			throw new ServiceException("Some fields are empty");
		}
		
		Order order = new Order();
		order.setTitle(title);
		order.setDescription(description);
		order.setSpecification(specification);
		java.util.Date utilDate = new java.util.Date();
	    Date sqlDate = new Date(utilDate.getTime());
		order.setDateCreated(sqlDate);
		order.setDateStart(Date.valueOf(dateStart));
		order.setDateFinish(Date.valueOf(dateFinish));
		order.setCustomer(customer);

		List<Work> works = prepareWorks(workIds);
		HashMap<Qualification, Integer> qualifications = prepareQualifications(qualificationsIdsAndCount);
		
		OrderDto orderDto = new OrderDto();
		orderDto.setOrder(order);
		orderDto.setWorks(works);
		orderDto.setQualifications(qualifications);
		try {
			OrderDto orderCreated = orderDao.add(orderDto);
		} catch (DaoException e) {
			
			e.printStackTrace();
		}	
		
		return null;
	}

	@Override
	public OrderDto getOrderById(String orderId) {
		OrderDto orderDto = orderDao.getById(Long.valueOf(orderId));
		
		return orderDto;
	}
	
	private List<Work> prepareWorks(String[] worksIds) {
		List<Work> works = new ArrayList<Work>();
		for ( int i = 0; i < worksIds.length; i++ ) {
			Work work = new Work();
			work.setId(Long.valueOf(worksIds[i]));
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
