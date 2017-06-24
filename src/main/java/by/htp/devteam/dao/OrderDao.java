package by.htp.devteam.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.Qualification;
import by.htp.devteam.bean.Work;
import by.htp.devteam.bean.dto.OrderListDto;

public interface OrderDao {
	OrderListDto getNewOrders(int offset, int countPerPage);
	
	Order getOrder(long id);
	
	List<Order> geOrdersByCustomer(Customer customer);
	
	Order addOrder(Order order);
	
	void addWorks(Order order, String[] ids);
	
	List<Work> getWorks(Order order);
	
	void addQualifications(Order order, HashMap<Qualification, Integer> qualifications);
	
	Map<Qualification, Integer> getQualifications(Order order);
	
}
