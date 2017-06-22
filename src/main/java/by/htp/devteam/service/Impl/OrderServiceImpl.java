package by.htp.devteam.service.Impl;

import java.sql.Date;
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
import by.htp.devteam.service.OrderQualificationService;
import by.htp.devteam.service.OrderService;
import by.htp.devteam.service.OrderWorkService;
import by.htp.devteam.service.ServiceException;
import by.htp.devteam.service.ServiceFactory;
import by.htp.devteam.util.SettingConstantValue;

public class OrderServiceImpl implements OrderService{

	private OrderDao orderDao;
	//private OrderQualificationService orderQualificationService;
	
	public OrderServiceImpl() {
		super();
		DaoFactory daoFactory = DaoFactory.getInstance();
		orderDao = daoFactory.getOrderDao();
		

		//orderQualificationService = serviceFactory.getOrderQualificationService();
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
			String[] workIds, String[] qualification) {
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
		Order orderCreated = orderDao.add(order);	
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderWorkService orderWorkService = serviceFactory.getOrderWorkService();
		orderWorkService.add(orderCreated, workIds);
		
		OrderQualificationService orderQualificationService = serviceFactory.getOrderQualificationService();
		orderQualificationService.add(orderCreated, qualification);
		return null;
	}

	@Override
	public OrderDto getOrderById(String orderId) {
		OrderDto orderDto = new OrderDto();

		Order order = orderDao.getById(Long.valueOf(orderId));
		System.out.println("service order=");
		System.out.println(order);
		orderDto.setOrder(order);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderWorkService orderWorkService = serviceFactory.getOrderWorkService();
		List<Work> works = orderWorkService.getByOrder(order);
		System.out.println("WORKS=");
		System.out.println(works);
		orderDto.setWorks(works);
		
		OrderQualificationService orderQualificationService = serviceFactory.getOrderQualificationService();
		Map<Qualification, Integer> qualifications = orderQualificationService.getByOrder(order);
		orderDto.setQualifications(qualifications);
		
		return orderDto;
	}
	
	
}
