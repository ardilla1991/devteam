package by.htp.devteam.dao;

import java.util.List;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.bean.dto.OrderListDto;

public interface OrderDao {
	OrderListDto getNewOrders(int offset, int countPerPage);
	
	Order getById(long id);
	
	List<Order> geOrdersByCustomer(Customer customer);
	
	Order add(Order order);
	
}
