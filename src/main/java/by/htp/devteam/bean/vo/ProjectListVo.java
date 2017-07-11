package by.htp.devteam.bean.vo;

import java.util.List;

import by.htp.devteam.bean.Project;

/**
 * Projects list object. Use for display list of objects with pagings settings.
 * Has properties <b>projects</b>, <b>countRecords</b>,
 * <b>countPages</b>, <b>currPage</b>
 * @author julia
 *
 */
public class ProjectListVo {
	
	/** List of projects on one page for display*/
	private List<Project> projects;
	
	/** Count records in full list*/
	private int countRecords;
	
	/** Count pages for dysplay in paging*/
	private int countPages;
	
	/** Current selected page*/
	private int currPage;
	
	public ProjectListVo() {
		super();
	}
	
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
