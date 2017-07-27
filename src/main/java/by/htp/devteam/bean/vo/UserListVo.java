package by.htp.devteam.bean.vo;

import java.util.List;

/**
 * User's list object. Use for display list of objects with pagings settings.
 * Has properties <b>users</b>, <b>countRecords</b>,
 * <b>countPages</b>, <b>currPage</b>
 * @author julia
 *
 */
public final class UserListVo {
	
	/** List of users (userVo) on one page for display*/
	private List<UserVo> users;
	
	/** Count records in full list*/
	private int countRecords;
	
	/** Count pages for dysplay in paging*/
	private int countPages;
	
	/** Current selected page*/
	private int currPage;
	
	public UserListVo() {
		super();
	}

	public List<UserVo> getUsers() {
		return users;
	}

	public void setUsers(List<UserVo> users) {
		this.users = users;
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
