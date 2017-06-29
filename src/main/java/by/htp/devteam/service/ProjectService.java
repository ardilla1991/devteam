package by.htp.devteam.service;

import by.htp.devteam.bean.Project;
import by.htp.devteam.bean.dto.OrderDto;
import by.htp.devteam.bean.dto.ProjectListDto;

public interface ProjectService {

	ProjectListDto fetchAll(String currPage) throws ServiceException;
	Project add(OrderDto order, String title, String description, String[] qualification) throws ServiceException;
	
}
