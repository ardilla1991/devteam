package by.htp.devteam.dao;

import java.sql.Connection;
import java.util.List;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.dto.OrderVo;
import by.htp.devteam.bean.dto.OrderListVo;

public interface OrderDao {
	OrderListVo getNewOrders(int offset, int countPerPage) throws DaoException;
	
	OrderVo getById(long id) throws DaoException;
	
	List<Order> getByCustomer(Customer customer) throws DaoException;
	
	OrderVo add(OrderVo orderVo) throws DaoException;
	
	void setPrice(Connection connection, Order order) throws DaoException;
	
}
