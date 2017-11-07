package by.htp.devteam.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import org.hibernate.Session;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.ObjectNotFoundException;
import by.htp.devteam.bean.vo.OrderVo;

/**
 * Interface for order's Service layer.
 * Do business logic including validation and dao exceptions.
 * Select data from storage using DAO object
 * @author julia
 *
 */
public interface OrderService {
	
	/**
	 * Get list of all new orders.
	 * New orders are orders without set price
	 * Records are selected according to current page. 
	 * Method checks if page has a correct value. If not - throw exception. 
	 * Also set up parameters to select records ( LIMIT )
	 * @param currPage Current selected page
	 * @return {@link  by.htp.devteam.bean.vo.PagingVo}
	 * @throws ServiceException  after catching DAOException
	 */
	PagingVo<Order> getNewOrders(String currPage) throws ServiceException;
	
	/**
	 * Get list of orders by customer
	 * @param customer Customer information
	 * @return list of orders
	 * @throws ServiceException  after catching DAOException
	 */
	List<Order> geOrdersByCustomer(Customer customer) throws ServiceException;
	
	/**
	 * Add order in storage.
	 * Before save method validates fields. 
	 * If not correct file name or some feilds - throw exception and stop data proccessing. 
	 * After that method try to upload file in folder for uploads
	 * @param customer Customer information
	 * @param title Order title
	 * @param description Order description
	 * @param specification Part() param for upload file
	 * @param dateStart start date of project
	 * @param dateFinish finish date of project
	 * @param workIds list of works 
	 * @param qualifications Map of qualification as key and count needed qualification as a value
	 * @return Order : full order information with works information and needed qualifications 
	 * @see by.htp.devteam.bean.Order#Order()
	 * @throws ServiceException When error happen with validation, file upload or DAOException are catched
	 */
	Order add(Customer customer, String title, String description, Part specification, String dateStart, 
			String dateFinish, String[] workIds, Map<String, String> qualifications) throws ServiceException;

	/**
	 * Get order with all information : order record plus works list and qualification with count employees.
	 * Method checks if order has a correct id's value. If not - throw exception. 
	 * @param orderId id of order
	 * @return OrderVo order information with checked works and qualifications
	 * @see by.htp.devteam.bean.vo.OrderVo
	 * @throws ServiceException after catching DAOException
	 * @throws ObjectNotFoundException If order is not exist
	 */
	OrderVo getById(String orderId) throws ServiceException, ObjectNotFoundException;
	
	/**
	 * Set price and date processing for order. Update after creating project
	 * @param session Hibernate session 
	 * @param order With updated price and dateProcessing values
	 * @throws ServiceException after catching DAOException
	 */
	void setPriceAndDateProcessing(Session session, Order order) throws ServiceException;
}
