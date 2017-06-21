package by.htp.devteam.service;

import by.htp.devteam.bean.Customer;

public interface CustomerService {
	Customer authorise(String login, String password) throws ServiceException;
	boolean logOut(Customer customer);
}
