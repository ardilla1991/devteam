package by.htp.devteam.dao;

import java.util.List;

import by.htp.devteam.bean.Qualification;

/**
 * Interface for qualification's DAO layer
 * @author julia
 *
 */
public interface QualificationDao {
	
	/**
	 * Get all qualifications we can set on project (use service flag is false)
	 * @return list of qualifications' objects
	 * @throws DaoException
	 */
	List<Qualification> fetchAll() throws DaoException;
}
