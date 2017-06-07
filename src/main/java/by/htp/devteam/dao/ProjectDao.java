package by.htp.devteam.dao;

import by.htp.devteam.bean.dto.ProjectDto;

public interface ProjectDao {

	ProjectDto getNewProjects(int offset, int countPerPage);
}
