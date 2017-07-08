package by.htp.devteam.service;

import by.htp.devteam.bean.User;

public interface UserService {
	User authorise(String login, String password) throws ServiceException;
	void logout(User user);
}
