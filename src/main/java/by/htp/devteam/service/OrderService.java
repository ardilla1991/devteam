package by.htp.devteam.service;

import java.util.List;
import java.util.Map;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.dto.OrderVo;
import by.htp.devteam.bean.dto.OrderListVo;

public interface OrderService {
	OrderListVo getNewOrders(String currPage) throws ServiceException;
	
	List<Order> geOrdersByCustomer(Customer customer) throws ServiceException;
	
	Order add(Customer customer, String title, String description, String specification, String dateStart, 
			String dateFinish, String[] workIds, Map<String, String> qualifications) throws ServiceException;
	
	OrderVo getOrderById(String orderId) throws ServiceException;
}
