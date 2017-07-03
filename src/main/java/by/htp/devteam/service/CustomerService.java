package by.htp.devteam.service;

import by.htp.devteam.bean.Customer;
import by.htp.devteam.bean.User;

public interface CustomerService {

	Customer getByUser(User user) throws ServiceException;

}
