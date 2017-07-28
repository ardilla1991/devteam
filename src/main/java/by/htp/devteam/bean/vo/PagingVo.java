package by.htp.devteam.bean.vo;

import java.util.List;

/**
 * List of objects. Use for display list of objects with pagings settings.
 * Has properties <b>records</b>, <b>countRecords</b>,
 * <b>countPages</b>, <b>currPage</b>
 * @author julia
 *
 */
public class PagingVo<T> {
	
	/** List of records on one page for display*/
	private List<T> records;
	
	/** Count records in full list*/
	private int countAllRecords;
	
	/** Count pages for dysplay in paging*/
	private int countPages;
	
	/** Current selected page*/
	private int currPage;
	
	public PagingVo() {
		super();
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public int getCountAllRecords() {
		return countAllRecords;
	}

	public void setCountAllRecords(int countAllRecords) {
		this.countAllRecords = countAllRecords;
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
