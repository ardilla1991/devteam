package by.htp.devteam.dao;

import by.htp.devteam.bean.User;
import by.htp.devteam.bean.dto.UserListVo;

public interface UserDao {
	User fetchByCredentials(String login) throws DaoException;
	UserListVo fetchAll(int offset, int countPerPage) throws DaoException;
}
