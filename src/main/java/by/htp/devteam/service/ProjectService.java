package by.htp.devteam.service;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.bean.dto.ProjectListDto;

public interface ProjectService {

	ProjectListDto fetchAll(String currPage) throws ServiceException;
	ProjectListDto fetchAll(Employee employee, String currPage) throws ServiceException;
	Project add(OrderDto orderDto, String title, String description, String[] qualification, String price) throws ServiceException;
	
}
