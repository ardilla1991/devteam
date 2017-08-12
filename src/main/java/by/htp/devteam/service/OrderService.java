package by.htp.devteam.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.Order;
import by.htp.devteam.bean.vo.PagingVo;
import by.htp.devteam.controller.ObjectNotFoundExeption;
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
	 * Rocords are selected according to current page. 
	 * Method checks if page has a correct value. If not - throw exception. 
	 * Also set up parameters to select records ( LIMIT )
	 * @param currPage Current selected page
	 * @return {@link  by.htp.devteam.bean.vo.PagingVo}
	 * @throws ServiceException  after catching DAOException
	 */
	PagingVo<Order> getNewOrders(String currPage) throws ServiceException;
	
	/**
	 * Get list of orders by customer
	 * @param customer
	 * @return list of orders
	 * @throws ServiceException
	 */
	List<Order> geOrdersByCustomer(Customer customer) throws ServiceException;
	
	/**
	 * Add order in storage.
	 * Before save method validates fields. 
	 * If not correct file name or some feilds - throw exception and stop data proccessing. 
	 * After that method try to upload file in folder for uploads
	 * @param customer
	 * @param title
	 * @param description
	 * @param specification Part() param for upload file
	 * @param dateStart start date of project
	 * @param dateFinish finish date of project
	 * @param workIds list of works 
	 * @param qualifications Map of qualification as key and count needed qualification as a value
	 * @return OrdeVo : order information, works' information and needed qualification 
	 * @see by.htp.devteam.bean.vo.OrderVo#OrderVo()
	 * @throws ServiceException
	 */
	OrderVo add(Customer customer, String title, String description, Part specification, String dateStart, 
			String dateFinish, String[] workIds, Map<String, String> qualifications) throws ServiceException;

	/**
	 * Get order with all information : order record plus works list and qualification with count employees.
	 * Method checks if order has a correct id's value. If not - throw exception. 
	 * @param orderId id of order
	 * @return OrderVo order information with checked works and qualifications
	 * @see by.htp.devteam.bean.vo.OrderVo
	 * @throws ServiceException
	 * @throws ObjectNotFoundExeption
	 */
	OrderVo getById(String orderId) throws ServiceException, ObjectNotFoundExeption;
	
	/**
	 * Set price and date processing for order. Update after creating project
	 * @param connection
	 * @param order With udated price and dateProcessing values
	 * @throws ServiceException
	 */
	void setPriceAndDateProcessing(Connection connection, Order order) throws ServiceException;
}
