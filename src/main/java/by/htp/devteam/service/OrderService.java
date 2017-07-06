package by.htp.devteam.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.dto.OrderVo;
import by.htp.devteam.bean.dto.OrderListVo;

public interface OrderService {
	OrderListVo getNewOrders(String currPage) throws ServiceException;
	
	List<Order> geOrdersByCustomer(Customer customer) throws ServiceException;
	
	OrderVo add(Customer customer, String title, String description, Part specification, String dateStart, 
			String dateFinish, String[] workIds, Map<String, String> qualifications, String applicationPath ) throws ServiceException;
	
	OrderVo getOrderById(String orderId) throws ServiceException;
}
