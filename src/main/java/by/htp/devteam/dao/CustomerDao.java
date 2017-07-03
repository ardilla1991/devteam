package by.htp.devteam.dao;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.User;

public interface CustomerDao {

	Customer getCustomerByUser(User user) throws DaoException;
}
