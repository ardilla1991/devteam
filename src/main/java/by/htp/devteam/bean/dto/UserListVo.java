package by.htp.devteam.bean.dto;

import java.util.List;

public final class UserListVo {
	
	private List<UserVo> users;
	private int countRecords;
	private int countPages;
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
