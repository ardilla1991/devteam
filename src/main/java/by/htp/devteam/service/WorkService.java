package by.htp.devteam.service;

import java.util.List;

import by.htp.devteam.bean.Work;

public interface WorkService {

	List<Work> fetchAll() throws ServiceException;
}
