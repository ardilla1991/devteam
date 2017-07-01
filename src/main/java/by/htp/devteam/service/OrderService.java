package by.htp.devteam.service;

import java.util.List;
import java.util.Map;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;
import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.bean.dto.OrderListDto;

public interface OrderService {
	OrderListDto getNewOrders(String currPage) throws ServiceException;
	List<Order> geOrdersByCustomer(Customer customer);
	Order add(Customer customer, String title, String description, String specification, String dateStart, 
			String dateFinish, String[] workIds, Map<String, String> qualifications) throws ServiceException;
	OrderDto getOrderById(String orderId);
	
	List<Work> getWorks(Order order);
	void addWorks(Order order, String[] ids);
	void addQualifications(Order order, Map<String, String> qualifications);
	Map<Qualification, Integer> getQualifications(Order order);
}
