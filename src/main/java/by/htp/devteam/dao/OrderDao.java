package by.htp.devteam.dao;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;
import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.bean.dto.OrderListDto;

public interface OrderDao {
	OrderListDto getNewOrders(int offset, int countPerPage);
	
	OrderDto getById(long id);
	
	List<Order> getByCustomer(Customer customer);
	
	OrderDto add(OrderDto orderDto) throws DaoException;
	
	//List<Work> getWorks(Order order);
	
	//Map<Qualification, Integer> getQualifications(Order order);
	
	void setPrice(Connection connection, Order order);
	
}
