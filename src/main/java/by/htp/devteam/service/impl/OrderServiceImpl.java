package by.htp.devteam.service.impl;

import java.sql.Date;
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
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.OrderDao;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.util.SettingConstantValue;

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

		return orderDao.geOrdersByCustomer(customer);
	}

	@Override
	public Order add(Customer customer, String title, String description, String specification, String dateStart, String dateFinish,
			String[] workIds, Map<String, String> qualifications) {
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
		Order orderCreated = orderDao.addOrder(order);	
		
		addWorks(orderCreated, workIds);
		addQualifications(orderCreated, qualifications);
		
		return null;
	}

	@Override
	public OrderDto getOrderById(String orderId) {
		OrderDto orderDto = new OrderDto();
System.out.println("orderId" + orderId);
		Order order = orderDao.getOrder(Long.valueOf(orderId));
		orderDto.setOrder(order);
		orderDto.setWorks(getWorks(order));
		orderDto.setQualifications(getQualifications(order));
		
		return orderDto;
	}
	
	@Override
	public void addWorks(Order order, String[] ids) {
		orderDao.addWorks(order, ids);
	}
	
	@Override
	public List<Work> getWorks(Order order) {
		return orderDao.getWorks(order);
	}
	
	@Override
	public void addQualifications(Order order, Map<String, String> qualifications) {
		HashMap<Qualification, Integer> qualificationsList = new HashMap<Qualification, Integer>();
		Iterator it = qualifications.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			Qualification qualification = new Qualification();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			qualification.setId(Long.valueOf((String) pair.getKey()));
			
			qualificationsList.put(qualification, Integer.valueOf((String) pair.getValue()));
		}
		orderDao.addQualifications(order, qualificationsList);	
	}
	
	@Override
	public Map<Qualification, Integer> getQualifications(Order order) {
		return orderDao.getQualifications(order);
	}
	
}
