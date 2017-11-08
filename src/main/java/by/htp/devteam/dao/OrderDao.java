package by.htp.devteam.dao;

import java.sql.Connection;
import java.util.List;

import org.hibernate.Session;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.vo.OrderVo;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.ObjectNotFoundException;

/**
 * Interface for order's DAO layer
 * @author julia
 *
 */
public interface OrderDao {
	
	/**
	 * Get list of new orders. 
	 * New orders are orders without set price
	 * @param offset Offset to get list
	 * @param countPerPage Count records per page
	 * @return orders list according current page 
	 * plus page settings (all pages count, current page number)
	 * @see by.htp.devteam.bean.vo.PagingVo#PagingVo()
	 * @throws DaoException When SQLException are catched
	 */
	PagingVo<Order> getNewOrders(int offset, int countPerPage) throws DaoException;
	
	/**
	 * Get order with all information : order record plus works list and qualification with count employees 
	 * @param id id of order
	 * @return OrderVo order information with checked works and qualifications
	 * @see by.htp.devteam.bean.vo.OrderVo
	 * @throws DaoException When SQLException are catched
	 * @throws ObjectNotFoundException When the order is not exist
	 */
	OrderVo getById(long id) throws DaoException, ObjectNotFoundException;
	
	/**
	 * Get list of orders by customer
	 * @param customer Customer information
	 * @return Orders list by customer 
	 * @throws DaoException When SQLException are catched
	 */
	List<Order> getByCustomer(Customer customer) throws DaoException;
	
	/**
	 * Add order: order information, selected work information and needed qualifications and count
	 * @param order {@link by.htp.devteam.bean.Order#Order()} Order information
	 * @return Order : order information, works information and needed qualifications
	 * @throws DaoException When SQLException are catched
	 */
	Order add(Order order) throws DaoException;
	
	/**
	 * Set price and date processing for order. Update after creating project
	 * @param session Hibernate session 
	 * @param order Order information
	 * @throws DaoException When SQLException are catched
	 */
	void setPriceAndDateProcessing(Session session, Order order) throws DaoException;
	
}
