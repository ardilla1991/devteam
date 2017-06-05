package by.htp.devteam.service;

import java.util.List;

import by.htp.devteam.bean.Project;

public interface ProjectService {

	List<Project> getNewProjects(String currPage);
}
