package by.htp.devteam.service;

import java.util.List;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.OrderVo;
import by.htp.devteam.bean.dto.ProjectVo;
import by.htp.devteam.bean.dto.ProjectListVo;

public interface ProjectService {

	ProjectListVo fetchAll(String currPage, Employee employee) throws ServiceException;
	
	Project add(OrderVo orderVo, String title, String description, String[] qualification, String price) 
			throws ServiceException;
	
	ProjectVo getById(String id) throws ServiceException;
	
	void updateHours(String id, Employee employee, String hours) throws ServiceException;
	
	List<Project> findByTitle(String title) throws ServiceException;
}
