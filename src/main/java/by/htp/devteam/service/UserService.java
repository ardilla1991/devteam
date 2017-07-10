package by.htp.devteam.service;

import by.htp.devteam.bean.User;
import by.htp.devteam.bean.dto.UserListVo;

public interface UserService {
	User authorise(String login, String password) throws ServiceException;
	void logout(User user);
	UserListVo fetchAll(String currPage) throws ServiceException;
}
