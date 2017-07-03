package by.htp.devteam.dao;

import by.htp.devteam.bean.User;

public interface UserDao {
	User fetchByCredentials(String login, String password) throws DaoException;
}
