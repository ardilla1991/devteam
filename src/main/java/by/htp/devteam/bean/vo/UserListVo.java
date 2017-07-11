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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + countPages;
		result = prime * result + countRecords;
		result = prime * result + currPage;
		result = prime * result + ((users == null) ? 0 : users.hashCode());
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
		UserListVo other = (UserListVo) obj;
		if (countPages != other.countPages)
			return false;
		if (countRecords != other.countRecords)
			return false;
		if (currPage != other.currPage)
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}
	
}
