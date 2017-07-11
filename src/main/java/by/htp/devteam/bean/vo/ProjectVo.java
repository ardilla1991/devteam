package by.htp.devteam.bean.vo;

import java.util.Map;

import by.htp.devteam.bean.Employee;
import by.htp.devteam.bean.Project;

/**
 * Project's object. Use for display object with all information for chosen employees.
 * Has properties <b>project</b>, <b>employee</b>
 * @author julia
 *
 */
public class ProjectVo {
	
	/** Project's object */
	private Project project;
	
	/** Map of employees with spending times on a ptoject for each employee*/
	private Map<Employee, Integer> employees;
	
	public ProjectVo() {
		super();
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public Map<Employee, Integer> getEmployee() {
		return employees;
	}
	
	public void setEmployee(Map<Employee, Integer> employees) {
		this.employees = employees;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employees == null) ? 0 : employees.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectVo other = (ProjectVo) obj;
		if (employees == null) {
			if (other.employees != null)
				return false;
		} else if (!employees.equals(other.employees))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		return true;
	}
}
