package by.htp.devteam.dao;

import by.htp.devteam.bean.Customer;

public interface CustomerDao {
	Customer fetchByCredentials(String login, String password);
}
