package by.htp.devteam.bean.dto;

import java.util.Map;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;

public class ProjectDto {
	private Project project;
	private Map<Employee, Integer> employee;
	
	public ProjectDto() {
		super();
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public Map<Employee, Integer> getEmployee() {
		return employee;
	}
	
	public void setEmployee(Map<Employee, Integer> employee) {
		this.employee = employee;
	}
}
