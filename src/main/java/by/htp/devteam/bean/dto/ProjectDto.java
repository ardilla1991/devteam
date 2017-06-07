package by.htp.devteam.bean.dto;

import java.util.List;

import by.htp.devteam.bean.Project;

public class ProjectDto {
	
	private List<Project> projects;
	private int countRecords;
	private int countPages;
	private int currPage;
	
	public List<Project> getProjects() {
		return projects;
	}
	
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	public int getCountRecords() {
		return countRecords;
	}
	
	public void setCountRecords(int countRecords) {
		this.countRecords = countRecords;
	}

	public int getCountPages() {
		return countPages;
	}

	public void setCountPages(int countPages) {
		this.countPages = countPages;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
}
