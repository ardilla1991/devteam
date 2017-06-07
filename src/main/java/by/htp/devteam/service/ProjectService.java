package by.htp.devteam.service;

import by.htp.devteam.bean.dto.ProjectDto;

public interface ProjectService {

	ProjectDto getNewProjects(String currPage) throws ServiceException;
}
