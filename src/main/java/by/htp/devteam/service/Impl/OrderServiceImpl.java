package by.htp.devteam.service.Impl;

import java.sql.Date;
import java.util.List;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.dto.OrderListDto;
import by.htp.devteam.bean.dto.ProjectDto;
import by.htp.devteam.dao.DaoFactory;
import by.htp.devteam.dao.OrderDao;
import by.htp.devteam.dao.ProjectDao;
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
			String[] workIds, String[] qualification) {
		Order order = new Order();
		order.setTitle(title);
		order.setDescription(description);
		order.setSpecification(specification);
		order.setDateStart(Date.valueOf(dateStart));
		order.setDateFinish(Date.valueOf(dateFinish));
		order.setCustomer(customer);
		System.out.println("OKKKKK");
		Order orderCreated = orderDao.add(order);
		return null;
	}
	
	
}
