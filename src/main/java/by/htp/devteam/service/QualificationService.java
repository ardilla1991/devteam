package by.htp.devteam.service;

import java.util.List;

import by.htp.devteam.bean.Qualification;

public interface QualificationService {
	List<Qualification> fetchAll() throws ServiceException;
}
