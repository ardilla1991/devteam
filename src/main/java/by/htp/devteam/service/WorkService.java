package by.htp.devteam.service;

import java.util.List;

import by.htp.devteam.bean.Work;

/**
 * Interface for work's Service layer.
 * Do business logic including validation and loggin exceptions.
 * Select data from storage using DAO object
 * @author julia
 *
 */
public interface WorkService {

	/**
	 * Get all existing works
	 * @return list of works' objects
	 * @throws ServiceException after catching DAOException
	 */
	List<Work> fetchAll() throws ServiceException;
}
