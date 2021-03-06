package by.htp.devteam.service;

import java.util.List;

import by.htp.devteam.bean.Qualification;

/**
 * Interface for qualification's Service layer.
 * Do business logic including validation and dao exceptions
 * Select data from storage using DAO object
 * @author julia
 *
 */
public interface QualificationService {
	
	/**
	 * Get all qualifications we can set on project (use service flag is false)
	 * @return list of qualifications' objects
	 * @throws ServiceException After DAOException are catched
	 */
	List<Qualification> fetchAll() throws ServiceException;
}
