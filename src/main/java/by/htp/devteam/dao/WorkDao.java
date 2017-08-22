package by.htp.devteam.dao;

import java.util.List;

import by.htp.devteam.bean.Work;

/**
 * Interface for work's DAO layer
 * @author julia
 *
 */
public interface WorkDao {
	
	/**
	 * Get all existing works
	 * @return list of works' objects
	 * @throws DaoException When SQLException are catched
	 */
	List<Work> fetchAll() throws DaoException;
}
