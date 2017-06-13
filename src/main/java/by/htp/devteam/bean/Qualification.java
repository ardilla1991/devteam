package by.htp.devteam.bean;

import java.io.Serializable;

public class Qualification implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3700326780172626583L;
	private Long id;
	private String title;
	
	public Qualification() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
