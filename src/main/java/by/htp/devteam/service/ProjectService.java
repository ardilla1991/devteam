package by.htp.devteam.service;

import java.util.List;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.vo.OrderVo;
import by.htp.devteam.bean.vo.ProjectListVo;
import by.htp.devteam.bean.vo.ProjectVo;

public interface ProjectService {

	ProjectListVo fetchAll(String currPage, Employee employee) throws ServiceException;
	
	Project add(OrderVo orderVo, String title, String description, String[] qualification, String price) 
			throws ServiceException;
	
	ProjectVo getById(String id) throws ServiceException;
	
	void updateHours(String id, Employee employee, String hours) throws ServiceException;
	
	List<Project> findByTitle(String title) throws ServiceException;
}
