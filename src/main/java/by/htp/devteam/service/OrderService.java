package by.htp.devteam.service;

import java.util.List;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.bean.dto.OrderListDto;

public interface OrderService {
	OrderListDto getNewOrders(String currPage) throws ServiceException;
	List<Order> geOrdersByCustomer(Customer customer);
	Order add(Customer customer, String title, String description, String specification, String dateStart, 
			String dateFinish, String[] workIds, String[] qualification);
	OrderDto getOrderById(String orderId);
}
